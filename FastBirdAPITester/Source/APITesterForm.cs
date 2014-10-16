using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Windows.Forms;
using FastBirdAPIObjects;
using GoldenHills.FastBird.ClientArea.Web.API;


namespace FastBirdAPITester
{
    public partial class APITesterForm : Form
    {
        private const string STR_Description = "Description";
        private const string STR_Id = "Id";
        public APITesterForm()
        {
            InitializeComponent();
        }

        private void createOrderButton_Click(object sender, EventArgs e)
        {
            var createOrderInput = new CreateOrderInput();
            createOrderInput.username = usernameTextBox.Text;
            createOrderInput.password = passwordTextBox.Text;
            createOrderInput.pickupaddress = pickupAddressComboBox.SelectedValue.ToString(); 
            createOrderInput.contactname = contactNameTextBox.Text;
            createOrderInput.phone1 = phone1TextBox.Text;
            createOrderInput.phone2 = phone2TextBox.Text;
            createOrderInput.flatno = flatNoTextBox.Text;
            createOrderInput.buildingno = buildingNoTextBox.Text;
            createOrderInput.blockno = blockNoTextBox.Text;
            createOrderInput.road = roadTextBox.Text;
            createOrderInput.location = locationComboBox.SelectedValue.ToString();
            createOrderInput.note = notesTextBox.Text;
            createOrderInput.packagetype = packageTypeComboBox.SelectedValue.ToString();
            createOrderInput.servicetype = serviceTypeComboBox.SelectedValue.ToString();
            createOrderInput.deliverytime = preferredDeliveryTimeComboBox.SelectedValue.ToString();
            createOrderInput.moneydeliverytype = moneyDeliveryTypeComboBox.SelectedValue.ToString();
            createOrderInput.collectionamount = int.Parse(collectionAmountTextBox.Text);
            createOrderInput.paymentmethod = Convert.ToInt32(paymentMethodComboBox.SelectedValue);
         

            
            string postData = JSONHelper.Serialize<CreateOrderInput>(createOrderInput); //encode your data 



            using (Stream s = GetResponseStream(orderWSTextBox.Text + "/Create", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    //decode jsonData with javascript serializer

                    var result = JSONHelper.Deserialize<CreateOrderResult>(jsonData);

                    if (!String.IsNullOrEmpty(result.d.Error))
                        MessageBox.Show("ERROR:" + result.d.Error);
                    else
                    {
                        MessageBox.Show(String.Format("SUCCESS: FBD = {0}, FastPayCode:{1}" , result.d.FBDNumber,result.d.FastPayCode));
                        fbdNumberTextBox.Text = result.d.FBDNumber;
                        GetOrderStatusHistory();
                        GetOrderApprovalStatus();
                    }

                }
            }

        }

     

        private Stream GetResponseStream(string methodName, string postData)
        {
            WebRequest request = WebRequest.Create(String.Format("{0}/{1}"
                , aPIUrlTextBox.Text, methodName));

            request.Method = "POST";
            request.ContentType = "application/json; charset=utf-8";

           
            using (Stream s = request.GetRequestStream())
            {
                using (StreamWriter sw = new StreamWriter(s))
                    sw.Write(postData);
            }
            return request.GetResponse().GetResponseStream();
        }

        private void getOrderStatusButton_Click(object sender, EventArgs e)
        {
            GetOrderStatusHistory();
        }

        private void GetOrderStatusHistory()
        {
            var getOrderStatusInput = new GetOrderStatusInput() { fbdnumber = fbdNumberTextBox.Text };

            string postData = JSONHelper.Serialize<GetOrderStatusInput>(getOrderStatusInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(orderTrackingWSTextBox.Text + "/GetOrderStatus", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<GetOrderStatusResult>(jsonData);

                    statusTextBox.Text = result.d.CurrentStatus;
                    statusHistoryGridView.DataSource = result.d.History;

                }
            }
        }

     
        private void getOrderApprovalStatusButton_Click(object sender, EventArgs e)
        {
            GetOrderApprovalStatus();

        }

        private void GetOrderApprovalStatus()
        {
            var getOrderStatusInput = new GetOrderStatusInput() { fbdnumber = fbdNumberTextBox.Text };

            string postData = JSONHelper.Serialize<GetOrderStatusInput>(getOrderStatusInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(orderTrackingWSTextBox.Text + "/GetOrderApprovalStatus", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<GetOrderApprovalStatusResult>(jsonData);

                    approvalStatusTextBox.Text = result.d.ApprovalStatus;


                }
            }
        }

        private void listDeliveryTimesButton_Click(object sender, EventArgs e)
        {
            

        }

        private void listMoneyDeliveryTypesButton_Click(object sender, EventArgs e)
        {


        }

    
        private void loadInitDataButton_Click(object sender, EventArgs e)
        {
            LoadLocations();
            LoadPackageTypes();
            LoadPreferredDeliveryTimes();
            LoadMoneyDeliveryTypes();
            LoadPaymentMethods();
            LoadPickupAddresses();
            GetClientCredits();    

        }

        private void GetClientCredits()
        {
            var getClientCreditInput = new GetClientCreditInput();
            getClientCreditInput.username = usernameTextBox.Text;
            getClientCreditInput.password = passwordTextBox.Text;

            string postData = JSONHelper.Serialize<GetClientCreditInput>(getClientCreditInput);




            using (Stream s = GetResponseStream(clientsWSTextBox.Text + "/CheckCredit", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<GetClientCreditResult>(jsonData);

                    if (!String.IsNullOrEmpty(result.d.Error))
                    {
                        MessageBox.Show(result.d.Error, "GetClientCredits");

                    }

                    clientCreditDataTextBox.Text = result.d.Credit.ToString();
                    



                }
            }
        }

        private void LoadLocations()
        {
            string postData = null;


            using (Stream s = GetResponseStream( mastersWSTextBox.Text+ "/ListLocations", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<ListLocationsResult>(jsonData);

                    if (!String.IsNullOrEmpty(result.d.Error))
                    {
                        MessageBox.Show(result.d.Error, "LoadLocations");

                    }
                    PopulateComboBoxList(locationComboBox, result.d.Locations);
                }
            }
        }



        private void LoadPackageTypes()
        {
            string postData = null;

            using (Stream s = GetResponseStream(mastersWSTextBox.Text + "/ListPackageTypes", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<ListPackageTypesResult>(jsonData);
                    if (!String.IsNullOrEmpty(result.d.Error))
                    {
                        MessageBox.Show(result.d.Error, "LoadPackageTypes");

                    }
                    PopulateComboBoxList(packageTypeComboBox, result.d.PackageTypes);
                }
            }
        }

        private void LoadServiceTypes()
        {
            
            if (pickupAddressComboBox.SelectedValue == null
                || locationComboBox.SelectedValue == null)
                return;

            var selectedAddress = (AddressDataLine)pickupAddressComboBox.SelectedItem;
            var loadSeriveTypeInput = new LoadSeriveTypeInput();

            loadSeriveTypeInput.pickuplocation = selectedAddress.LocationId.ToString();
            loadSeriveTypeInput.deliverylocation = locationComboBox.SelectedValue.ToString();

            string postData = JSONHelper.Serialize<LoadSeriveTypeInput>(loadSeriveTypeInput);




            using (Stream s = GetResponseStream(mastersWSTextBox.Text + "/ListServiceTypes", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<ListServiceTypesResult>(jsonData);
                    if (!String.IsNullOrEmpty(result.d.Error))
                    {
                        MessageBox.Show(result.d.Error, "LoadServiceTypes");

                    }
                    PopulateComboBoxList(serviceTypeComboBox, result.d.ServiceTypes);
                    GetServiceTypePrice();
                }
            }
        }

        private void LoadPreferredDeliveryTimes()
        {

            string postData = null;

            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(mastersWSTextBox.Text + "/ListDeliveryTimes", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<ListDeliveryTimesResult>(jsonData);
                    if (!String.IsNullOrEmpty(result.d.Error))
                    {
                        MessageBox.Show(result.d.Error, "LoadPreferredDeliveryTimes");

                    }
                    PopulateComboBoxList(preferredDeliveryTimeComboBox, result.d.DeliveryTimes);
                    

                }
            }
        }

        private void PopulateComboBoxList(ComboBox comboBox, object dataSource)
        {
            comboBox.DisplayMember = STR_Description;
            comboBox.ValueMember = STR_Id;
            comboBox.DataSource = dataSource;
        }

        private void LoadMoneyDeliveryTypes()
        {

            string postData = null;

            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(mastersWSTextBox.Text + "/ListMoneyDeliveryTypes", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<ListMoneyDeliveryTypesResult>(jsonData);
                    if (!String.IsNullOrEmpty(result.d.Error))
                    {
                        MessageBox.Show(result.d.Error, "LoadPreferredDeliveryTimes");

                    }
                    
                    PopulateComboBoxList(moneyDeliveryTypeComboBox, result.d.MoneyDeliveryTypes);


                }
            }
        }

        private void LoadPaymentMethods()
        {

            List<PaymentMethodItem> paymentMethodsList = new List<PaymentMethodItem>();
            paymentMethodsList.Add(new PaymentMethodItem(1, "Cash On Delivery"));
            paymentMethodsList.Add(new PaymentMethodItem(0, "Credit"));
            PopulateComboBoxList(paymentMethodComboBox,paymentMethodsList);
        }

        private class PaymentMethodItem
        {
            public PaymentMethodItem(int id, string description)
            {
                Id = id;
                Description = description;
            }
            public PaymentMethodItem()
            {
                Id = -1;
            }
             
            public int Id { get; set; }
            public string Description { get; set; }

        }

     

        private void  LoadPickupAddresses()
        {
            var listMyAddressInput = new ListMyAddressesInput();
            listMyAddressInput.username = usernameTextBox.Text;
            listMyAddressInput.password = passwordTextBox.Text;

            string postData = JSONHelper.Serialize<ListMyAddressesInput>(listMyAddressInput); 




            using (Stream s = GetResponseStream(clientsWSTextBox.Text + "/ListMyAddresses", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<ListPickupAddressesResult>(jsonData);

                    if (!String.IsNullOrEmpty(result.d.Error))
                    {
                        MessageBox.Show(result.d.Error,"LoadPickupAddresses");
                        
                    }
                    pickupAddressComboBox.DisplayMember = STR_Description;
                    pickupAddressComboBox.ValueMember = STR_Id;
                    pickupAddressComboBox.DataSource = result.d.Addresses;
                    

                    
                }
            }
        }

        

    

        private void locationComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            LoadServiceTypes();
        }

        private void pickupAddressComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            LoadServiceTypes();
        }

        private void serviceTypeComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            GetServiceTypePrice();

        }

        private void GetServiceTypePrice()
        {
            if (pickupAddressComboBox.SelectedValue == null
              || locationComboBox.SelectedValue == null
                || serviceTypeComboBox.SelectedValue == null)
                return;

            var selectedAddress = (AddressDataLine)pickupAddressComboBox.SelectedItem;
            var getSeriveTypeInput = new GetSeriveTypeInput();
            getSeriveTypeInput.servicetype= serviceTypeComboBox.SelectedValue.ToString();
            getSeriveTypeInput.pickuplocation = selectedAddress.LocationId.ToString();
            getSeriveTypeInput.deliverylocation = locationComboBox.SelectedValue.ToString();
            string postData = JSONHelper.Serialize<GetSeriveTypeInput>(getSeriveTypeInput);




            using (Stream s = GetResponseStream(mastersWSTextBox.Text + "/GetServiceTypePrice", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<GetServiceTypePriceResult>(jsonData);
                    if (!String.IsNullOrEmpty(result.d.Error))
                    {
                        MessageBox.Show(result.d.Error, "GetServiceTypePrice");
                    }
                    
                    serviceTypePriceTextBox.Text = result.d.ServiceTypePrice.ToString();
                }
            }
        }

        private void blockNoTextBox_Validating(object sender, System.ComponentModel.CancelEventArgs e)
        {
            if (String.IsNullOrEmpty(blockNoTextBox.Text))
                return;



            var getLocationByBlockNoInput = new GetLocationByBlockNoInput();
            getLocationByBlockNoInput.blockno = blockNoTextBox.Text;

            string postData = JSONHelper.Serialize<GetLocationByBlockNoInput>(getLocationByBlockNoInput);




            using (Stream s = GetResponseStream(mastersWSTextBox.Text + "/GetLocationByBlockNo", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<GetLocationByBlockNoResult>(jsonData);


                    if (result.d.Locations.Count > 0)
                    {
                        locationComboBox.SelectedValue = result.d.Locations[0].Id;
                    }
                }
            }
        }

        private void listMyAddressesButton_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(blockNoTextBox.Text))
                return;



            var getLocationByBlockNoInput = new GetLocationByBlockNoInput();
            getLocationByBlockNoInput.blockno = blockNoTextBox.Text;

            string postData = JSONHelper.Serialize<GetLocationByBlockNoInput>(getLocationByBlockNoInput);




            using (Stream s = GetResponseStream(mastersWSTextBox.Text + "/GetLocationByBlockNo", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<GetLocationByBlockNoResult>(jsonData);


                    if (result.d.Locations.Count > 0)
                    {
                        locationComboBox.SelectedValue = result.d.Locations[0].Id;
                    }
                }
            }
        }

        private void listInProgressOrdersButton_Click(object sender, EventArgs e)
        {
            var listOrdersInput = new ListOrdersInput() { username = usernameTextBox.Text,
                password=passwordTextBox.Text };

            string postData = JSONHelper.Serialize<ListOrdersInput>(listOrdersInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(orderWSTextBox.Text + "/ListInProgressOrders", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<ListOrdersResult>(jsonData);

                    //statusTextBox.Text = result.d.CurrentStatus;
                    ordersListGridView.DataSource = result.d.Orders;

                }
            }
        }

        private void listPendingOrders_Click(object sender, EventArgs e)
        {
var listOrdersInput = new ListOrdersInput() { username = usernameTextBox.Text,
                password=passwordTextBox.Text };

            string postData = JSONHelper.Serialize<ListOrdersInput>(listOrdersInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(orderWSTextBox.Text + "/ListPendingOrders", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<ListOrdersResult>(jsonData);

                    
                    ordersListGridView.DataSource = result.d.Orders;

                }
            }
        }

        private void listClosedOrdersButton_Click(object sender, EventArgs e)
        {
            var listOrdersInput = new ListOrdersInput()
            {
                username = usernameTextBox.Text,
                password = passwordTextBox.Text
            };

            string postData = JSONHelper.Serialize<ListOrdersInput>(listOrdersInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(orderWSTextBox.Text + "/ListClosedOrders", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<ListOrdersResult>(jsonData);


                    ordersListGridView.DataSource = result.d.Orders;

                }
            }
        }

        private void listReturnedMoneyOrdersButton_Click(object sender, EventArgs e)
        {
var listOrdersInput = new ListOrdersInput() { username = usernameTextBox.Text,
                password=passwordTextBox.Text };

            string postData = JSONHelper.Serialize<ListOrdersInput>(listOrdersInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(orderWSTextBox.Text + "/ListReturnedMoneyOrders", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    var result = JSONHelper.Deserialize<ListOrdersResult>(jsonData);

                    
                    ordersListGridView.DataSource = result.d.Orders;

                }



            }
        }

        private void getMyInformationButton_Click(object sender, EventArgs e)
        {
            var listOrdersInput = new GetClientCreditInput()
            {
                username = usernameTextBox.Text,
                password = passwordTextBox.Text
            };

            string postData = JSONHelper.Serialize<GetClientCreditInput>(listOrdersInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(clientsWSTextBox.Text + "/GetMyInformation", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                   

                }



            }
        }

        private void getCashHistoryButton_Click(object sender, EventArgs e)
        {
            var listOrdersInput = new GetClientCreditInput()
            {
                username = usernameTextBox.Text,
                password = passwordTextBox.Text
            };

            string postData = JSONHelper.Serialize<GetClientCreditInput>(listOrdersInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(moneyCollectionWSTextBox.Text + "/GetCashHistory", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    responseOutputTextBox.Text = jsonData; 
                    
                }



            }
        }

        private void getCashOnTheWayButton_Click(object sender, EventArgs e)
        {
 var listOrdersInput = new GetClientCreditInput()
            {
                username = usernameTextBox.Text,
                password = passwordTextBox.Text
            };

            string postData = JSONHelper.Serialize<GetClientCreditInput>(listOrdersInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(moneyCollectionWSTextBox.Text + "/GetCashOnTheWay", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    responseOutputTextBox.Text = jsonData; 
                    
                }



            }
        }

        private void addClientDeviceButton_Click(object sender, EventArgs e)
        {
            var addDeviceIdInputInput = new AddClientDeviceIDInput()
            {
                username = usernameTextBox.Text,
                password = passwordTextBox.Text,
                deviceid = clientDeviceIDTextBox.Text,
                note="test"
            };

            string postData = JSONHelper.Serialize<AddClientDeviceIDInput>(addDeviceIdInputInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(clientsWSTextBox.Text + "/AddDevice", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    responseOutputTextBox.Text = jsonData;

                }



            }
        }

        private void removeClientDeviceButton_Click(object sender, EventArgs e)
        {
            var addDeviceIdInputInput = new RemoveClientDeviceIDInput()
            {
                username = usernameTextBox.Text,
                password = passwordTextBox.Text,
                deviceid = clientDeviceIDTextBox.Text
            };

            string postData = JSONHelper.Serialize<RemoveClientDeviceIDInput>(addDeviceIdInputInput);



            //get response-stream, and use a streamReader to read the content
            using (Stream s = GetResponseStream(clientsWSTextBox.Text + "/RemoveDevice", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    var jsonData = sr.ReadToEnd();
                    responseOutputTextBox.Text = jsonData;

                }



            }
        }

        private void validateLoginButton_Click(object sender, EventArgs e)
        {
            var getClientCreditInput = new GetClientCreditInput();
            getClientCreditInput.username = usernameTextBox.Text;
            getClientCreditInput.password = passwordTextBox.Text;

            string postData = JSONHelper.Serialize<GetClientCreditInput>(getClientCreditInput);




            using (Stream s = GetResponseStream(clientsWSTextBox.Text + "/ValidateLogin", postData))
            {
                using (StreamReader sr = new StreamReader(s))
                {
                    responseOutputTextBox.Text =  sr.ReadToEnd();
                   




                }
            }
        }

    }
}
