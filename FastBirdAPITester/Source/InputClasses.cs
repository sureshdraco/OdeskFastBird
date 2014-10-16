using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GoldenHills.FastBird.ClientArea.Web.API
{
    public class CreateOrderInput
    {
        public string username {get;set;}
        public string password { get; set; }
        public string pickupaddress { get; set; }
        public string contactname { get; set; }

        public string phone1 { get; set; }
        public string phone2 { get; set; }
        public string flatno { get; set; }
        public string buildingno { get; set; }
        public string blockno { get; set; }
        public string road { get; set; }
        public string location { get; set; }
        public string note { get; set; }
        public string packagetype { get; set; }
        public string servicetype { get; set; }
        public string deliverytime { get; set; }
        public string moneydeliverytype { get; set; }
        public decimal collectionamount { get; set; }
        public int paymentmethod { get; set; }
        
    }

    public class GetOrderStatusInput
    {
        public string fbdnumber { get; set; }

    }

    public class ListMyAddressesInput
    {
        public string username { get; set; }
        public string password { get; set; }

    }

    public class ListOrdersInput
    {
        public string username { get; set; }
        public string password { get; set; }

    }

 public class GetClientCreditInput
    {
        public string username { get; set; }
        public string password { get; set; }

    }
 public class ValidateLoginInput
 {
     public string username { get; set; }
     public string password { get; set; }

 }
    public class LoadSeriveTypeInput
    {
        public string pickuplocation { get; set; }
        public string deliverylocation { get; set; }

    }

    public class GetSeriveTypeInput
    {
        public string servicetype { get; set; }
        public string pickuplocation { get; set; }
        public string deliverylocation { get; set; }
        

    }


    public class GetLocationByBlockNoInput
    {
        public string blockno { get; set; }
        

    }

    public class AddClientDeviceIDInput
    {
        public string username { get; set; }
        public string password { get; set; }
        public string deviceid { get; set; }
        public string note { get; set; }


    }

    public class RemoveClientDeviceIDInput
    {
        public string username { get; set; }
        public string password { get; set; }
        public string deviceid { get; set; }
        


    }

    

}