namespace FastBirdAPITester
{
    partial class APITesterForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.aPIUrlTextBox = new System.Windows.Forms.TextBox();
            this.createOrderButton = new System.Windows.Forms.Button();
            this.getOrderStatusButton = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.fbdNumberTextBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.orderWSTextBox = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.orderTrackingWSTextBox = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.mastersWSTextBox = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.clientsWSTextBox = new System.Windows.Forms.TextBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.approvalStatusTextBox = new System.Windows.Forms.TextBox();
            this.statusTextBox = new System.Windows.Forms.TextBox();
            this.label28 = new System.Windows.Forms.Label();
            this.label27 = new System.Windows.Forms.Label();
            this.statusHistoryGridView = new System.Windows.Forms.DataGridView();
            this.getOrderApprovalStatusButton = new System.Windows.Forms.Button();
            this.label7 = new System.Windows.Forms.Label();
            this.usernameTextBox = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.passwordTextBox = new System.Windows.Forms.TextBox();
            this.groupBox5 = new System.Windows.Forms.GroupBox();
            this.loadInitDataButton = new System.Windows.Forms.Button();
            this.moneyCollectionWSTextBox = new System.Windows.Forms.TextBox();
            this.label32 = new System.Windows.Forms.Label();
            this.groupBox6 = new System.Windows.Forms.GroupBox();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.label9 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.label14 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.label16 = new System.Windows.Forms.Label();
            this.label17 = new System.Windows.Forms.Label();
            this.label18 = new System.Windows.Forms.Label();
            this.label19 = new System.Windows.Forms.Label();
            this.label20 = new System.Windows.Forms.Label();
            this.label21 = new System.Windows.Forms.Label();
            this.label22 = new System.Windows.Forms.Label();
            this.label23 = new System.Windows.Forms.Label();
            this.label24 = new System.Windows.Forms.Label();
            this.pickupAddressComboBox = new System.Windows.Forms.ComboBox();
            this.addressTitleTextBox = new System.Windows.Forms.TextBox();
            this.contactNameTextBox = new System.Windows.Forms.TextBox();
            this.phone1TextBox = new System.Windows.Forms.TextBox();
            this.phone2TextBox = new System.Windows.Forms.TextBox();
            this.flatNoTextBox = new System.Windows.Forms.TextBox();
            this.buildingNoTextBox = new System.Windows.Forms.TextBox();
            this.roadTextBox = new System.Windows.Forms.TextBox();
            this.blockNoTextBox = new System.Windows.Forms.TextBox();
            this.notesTextBox = new System.Windows.Forms.TextBox();
            this.collectionAmountTextBox = new System.Windows.Forms.TextBox();
            this.paymentMethodComboBox = new System.Windows.Forms.ComboBox();
            this.label25 = new System.Windows.Forms.Label();
            this.packageTypeComboBox = new System.Windows.Forms.ComboBox();
            this.serviceTypeComboBox = new System.Windows.Forms.ComboBox();
            this.preferredDeliveryTimeComboBox = new System.Windows.Forms.ComboBox();
            this.moneyDeliveryTypeComboBox = new System.Windows.Forms.ComboBox();
            this.locationComboBox = new System.Windows.Forms.ComboBox();
            this.label26 = new System.Windows.Forms.Label();
            this.clientCreditDataTextBox = new System.Windows.Forms.TextBox();
            this.serviceTypePriceTextBox = new System.Windows.Forms.TextBox();
            this.label29 = new System.Windows.Forms.Label();
            this.label30 = new System.Windows.Forms.Label();
            this.label31 = new System.Windows.Forms.Label();
            this.listInProgressOrdersButton = new System.Windows.Forms.Button();
            this.listPendingOrders = new System.Windows.Forms.Button();
            this.ordersListGridView = new System.Windows.Forms.DataGridView();
            this.listClosedOrdersButton = new System.Windows.Forms.Button();
            this.listReturnedMoneyOrdersButton = new System.Windows.Forms.Button();
            this.getMyInformationButton = new System.Windows.Forms.Button();
            this.getCashHistoryButton = new System.Windows.Forms.Button();
            this.responseOutputTextBox = new System.Windows.Forms.TextBox();
            this.getCashOnTheWayButton = new System.Windows.Forms.Button();
            this.addClientDeviceButton = new System.Windows.Forms.Button();
            this.deleteClientDeviceButton = new System.Windows.Forms.Button();
            this.clientDeviceIDTextBox = new System.Windows.Forms.TextBox();
            this.label33 = new System.Windows.Forms.Label();
            this.validateLoginButton = new System.Windows.Forms.Button();
            this.groupBox2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.statusHistoryGridView)).BeginInit();
            this.groupBox5.SuspendLayout();
            this.groupBox6.SuspendLayout();
            this.tableLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.ordersListGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(6, 22);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(40, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "API Url";
            // 
            // aPIUrlTextBox
            // 
            this.aPIUrlTextBox.Location = new System.Drawing.Point(105, 19);
            this.aPIUrlTextBox.Name = "aPIUrlTextBox";
            this.aPIUrlTextBox.Size = new System.Drawing.Size(373, 20);
            this.aPIUrlTextBox.TabIndex = 1;
            this.aPIUrlTextBox.TabStop = false;
            this.aPIUrlTextBox.Text = "http://www.fastbird.org/clients/api";
            // 
            // createOrderButton
            // 
            this.createOrderButton.Location = new System.Drawing.Point(3, 460);
            this.createOrderButton.Name = "createOrderButton";
            this.createOrderButton.Size = new System.Drawing.Size(81, 23);
            this.createOrderButton.TabIndex = 21;
            this.createOrderButton.Text = "Create";
            this.createOrderButton.UseVisualStyleBackColor = true;
            this.createOrderButton.Click += new System.EventHandler(this.createOrderButton_Click);
            // 
            // getOrderStatusButton
            // 
            this.getOrderStatusButton.Location = new System.Drawing.Point(178, 20);
            this.getOrderStatusButton.Name = "getOrderStatusButton";
            this.getOrderStatusButton.Size = new System.Drawing.Size(128, 23);
            this.getOrderStatusButton.TabIndex = 2;
            this.getOrderStatusButton.Text = "GetOrderStatus";
            this.getOrderStatusButton.UseVisualStyleBackColor = true;
            this.getOrderStatusButton.Click += new System.EventHandler(this.getOrderStatusButton_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(2, 25);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(28, 13);
            this.label2.TabIndex = 0;
            this.label2.Text = "FBD";
            // 
            // fbdNumberTextBox
            // 
            this.fbdNumberTextBox.Location = new System.Drawing.Point(55, 22);
            this.fbdNumberTextBox.Name = "fbdNumberTextBox";
            this.fbdNumberTextBox.Size = new System.Drawing.Size(117, 20);
            this.fbdNumberTextBox.TabIndex = 1;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(6, 48);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(54, 13);
            this.label3.TabIndex = 0;
            this.label3.Text = "Order WS";
            // 
            // orderWSTextBox
            // 
            this.orderWSTextBox.Location = new System.Drawing.Point(105, 45);
            this.orderWSTextBox.Name = "orderWSTextBox";
            this.orderWSTextBox.Size = new System.Drawing.Size(122, 20);
            this.orderWSTextBox.TabIndex = 2;
            this.orderWSTextBox.TabStop = false;
            this.orderWSTextBox.Text = "NOrder.svc";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(254, 48);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(99, 13);
            this.label4.TabIndex = 0;
            this.label4.Text = "Order Tracking WS";
            // 
            // orderTrackingWSTextBox
            // 
            this.orderTrackingWSTextBox.Location = new System.Drawing.Point(356, 45);
            this.orderTrackingWSTextBox.Name = "orderTrackingWSTextBox";
            this.orderTrackingWSTextBox.Size = new System.Drawing.Size(122, 20);
            this.orderTrackingWSTextBox.TabIndex = 3;
            this.orderTrackingWSTextBox.TabStop = false;
            this.orderTrackingWSTextBox.Text = "OrderTracking.svc";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(6, 74);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(65, 13);
            this.label5.TabIndex = 0;
            this.label5.Text = "Masters WS";
            // 
            // mastersWSTextBox
            // 
            this.mastersWSTextBox.Location = new System.Drawing.Point(105, 71);
            this.mastersWSTextBox.Name = "mastersWSTextBox";
            this.mastersWSTextBox.Size = new System.Drawing.Size(122, 20);
            this.mastersWSTextBox.TabIndex = 4;
            this.mastersWSTextBox.TabStop = false;
            this.mastersWSTextBox.Text = "Masters.svc";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(254, 74);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(54, 13);
            this.label6.TabIndex = 0;
            this.label6.Text = "Client WS";
            // 
            // clientsWSTextBox
            // 
            this.clientsWSTextBox.Location = new System.Drawing.Point(356, 71);
            this.clientsWSTextBox.Name = "clientsWSTextBox";
            this.clientsWSTextBox.Size = new System.Drawing.Size(122, 20);
            this.clientsWSTextBox.TabIndex = 5;
            this.clientsWSTextBox.TabStop = false;
            this.clientsWSTextBox.Text = "Clients.svc";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.approvalStatusTextBox);
            this.groupBox2.Controls.Add(this.statusTextBox);
            this.groupBox2.Controls.Add(this.label28);
            this.groupBox2.Controls.Add(this.label27);
            this.groupBox2.Controls.Add(this.statusHistoryGridView);
            this.groupBox2.Controls.Add(this.fbdNumberTextBox);
            this.groupBox2.Controls.Add(this.label2);
            this.groupBox2.Controls.Add(this.getOrderApprovalStatusButton);
            this.groupBox2.Controls.Add(this.getOrderStatusButton);
            this.groupBox2.Location = new System.Drawing.Point(511, 15);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(451, 266);
            this.groupBox2.TabIndex = 2;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Order Tracking";
            // 
            // approvalStatusTextBox
            // 
            this.approvalStatusTextBox.Enabled = false;
            this.approvalStatusTextBox.Location = new System.Drawing.Point(315, 52);
            this.approvalStatusTextBox.Name = "approvalStatusTextBox";
            this.approvalStatusTextBox.Size = new System.Drawing.Size(129, 20);
            this.approvalStatusTextBox.TabIndex = 6;
            // 
            // statusTextBox
            // 
            this.statusTextBox.Enabled = false;
            this.statusTextBox.Location = new System.Drawing.Point(55, 52);
            this.statusTextBox.Name = "statusTextBox";
            this.statusTextBox.Size = new System.Drawing.Size(158, 20);
            this.statusTextBox.TabIndex = 6;
            // 
            // label28
            // 
            this.label28.AutoSize = true;
            this.label28.Location = new System.Drawing.Point(228, 55);
            this.label28.Name = "label28";
            this.label28.Size = new System.Drawing.Size(82, 13);
            this.label28.TabIndex = 5;
            this.label28.Text = "Approval Status";
            // 
            // label27
            // 
            this.label27.AutoSize = true;
            this.label27.Location = new System.Drawing.Point(7, 53);
            this.label27.Name = "label27";
            this.label27.Size = new System.Drawing.Size(37, 13);
            this.label27.TabIndex = 4;
            this.label27.Text = "Status";
            // 
            // statusHistoryGridView
            // 
            this.statusHistoryGridView.AllowUserToAddRows = false;
            this.statusHistoryGridView.AllowUserToDeleteRows = false;
            this.statusHistoryGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.statusHistoryGridView.Location = new System.Drawing.Point(9, 76);
            this.statusHistoryGridView.Name = "statusHistoryGridView";
            this.statusHistoryGridView.ReadOnly = true;
            this.statusHistoryGridView.Size = new System.Drawing.Size(434, 184);
            this.statusHistoryGridView.TabIndex = 3;
            // 
            // getOrderApprovalStatusButton
            // 
            this.getOrderApprovalStatusButton.Location = new System.Drawing.Point(312, 19);
            this.getOrderApprovalStatusButton.Name = "getOrderApprovalStatusButton";
            this.getOrderApprovalStatusButton.Size = new System.Drawing.Size(132, 23);
            this.getOrderApprovalStatusButton.TabIndex = 2;
            this.getOrderApprovalStatusButton.Text = "GetOrderApprovalStatus";
            this.getOrderApprovalStatusButton.UseVisualStyleBackColor = true;
            this.getOrderApprovalStatusButton.Click += new System.EventHandler(this.getOrderApprovalStatusButton_Click);
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(5, 126);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(55, 13);
            this.label7.TabIndex = 0;
            this.label7.Text = "Username";
            // 
            // usernameTextBox
            // 
            this.usernameTextBox.Location = new System.Drawing.Point(104, 123);
            this.usernameTextBox.Name = "usernameTextBox";
            this.usernameTextBox.Size = new System.Drawing.Size(373, 20);
            this.usernameTextBox.TabIndex = 6;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(6, 154);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(53, 13);
            this.label8.TabIndex = 0;
            this.label8.Text = "Password";
            // 
            // passwordTextBox
            // 
            this.passwordTextBox.Location = new System.Drawing.Point(105, 151);
            this.passwordTextBox.Name = "passwordTextBox";
            this.passwordTextBox.PasswordChar = '*';
            this.passwordTextBox.Size = new System.Drawing.Size(121, 20);
            this.passwordTextBox.TabIndex = 7;
            // 
            // groupBox5
            // 
            this.groupBox5.Controls.Add(this.loadInitDataButton);
            this.groupBox5.Controls.Add(this.aPIUrlTextBox);
            this.groupBox5.Controls.Add(this.passwordTextBox);
            this.groupBox5.Controls.Add(this.label8);
            this.groupBox5.Controls.Add(this.label1);
            this.groupBox5.Controls.Add(this.orderWSTextBox);
            this.groupBox5.Controls.Add(this.usernameTextBox);
            this.groupBox5.Controls.Add(this.label7);
            this.groupBox5.Controls.Add(this.label3);
            this.groupBox5.Controls.Add(this.orderTrackingWSTextBox);
            this.groupBox5.Controls.Add(this.label4);
            this.groupBox5.Controls.Add(this.moneyCollectionWSTextBox);
            this.groupBox5.Controls.Add(this.label32);
            this.groupBox5.Controls.Add(this.mastersWSTextBox);
            this.groupBox5.Controls.Add(this.label5);
            this.groupBox5.Controls.Add(this.clientsWSTextBox);
            this.groupBox5.Controls.Add(this.label6);
            this.groupBox5.Location = new System.Drawing.Point(17, 12);
            this.groupBox5.Name = "groupBox5";
            this.groupBox5.Size = new System.Drawing.Size(484, 190);
            this.groupBox5.TabIndex = 0;
            this.groupBox5.TabStop = false;
            this.groupBox5.Text = "WS Input Details";
            // 
            // loadInitDataButton
            // 
            this.loadInitDataButton.Location = new System.Drawing.Point(355, 151);
            this.loadInitDataButton.Name = "loadInitDataButton";
            this.loadInitDataButton.Size = new System.Drawing.Size(122, 23);
            this.loadInitDataButton.TabIndex = 8;
            this.loadInitDataButton.Text = "Load Init Data";
            this.loadInitDataButton.UseVisualStyleBackColor = true;
            this.loadInitDataButton.Click += new System.EventHandler(this.loadInitDataButton_Click);
            // 
            // moneyCollectionWSTextBox
            // 
            this.moneyCollectionWSTextBox.Location = new System.Drawing.Point(105, 97);
            this.moneyCollectionWSTextBox.Name = "moneyCollectionWSTextBox";
            this.moneyCollectionWSTextBox.Size = new System.Drawing.Size(122, 20);
            this.moneyCollectionWSTextBox.TabIndex = 4;
            this.moneyCollectionWSTextBox.TabStop = false;
            this.moneyCollectionWSTextBox.Text = "MoneyCollection.svc";
            // 
            // label32
            // 
            this.label32.AutoSize = true;
            this.label32.Location = new System.Drawing.Point(6, 100);
            this.label32.Name = "label32";
            this.label32.Size = new System.Drawing.Size(65, 13);
            this.label32.TabIndex = 0;
            this.label32.Text = "Masters WS";
            // 
            // groupBox6
            // 
            this.groupBox6.Controls.Add(this.tableLayoutPanel1);
            this.groupBox6.Location = new System.Drawing.Point(20, 208);
            this.groupBox6.Name = "groupBox6";
            this.groupBox6.Size = new System.Drawing.Size(485, 507);
            this.groupBox6.TabIndex = 1;
            this.groupBox6.TabStop = false;
            this.groupBox6.Text = "Create Order";
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 4;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 130F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 71F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Controls.Add(this.createOrderButton, 0, 21);
            this.tableLayoutPanel1.Controls.Add(this.label9, 0, 4);
            this.tableLayoutPanel1.Controls.Add(this.label10, 0, 2);
            this.tableLayoutPanel1.Controls.Add(this.label11, 0, 5);
            this.tableLayoutPanel1.Controls.Add(this.label12, 0, 6);
            this.tableLayoutPanel1.Controls.Add(this.label13, 2, 6);
            this.tableLayoutPanel1.Controls.Add(this.label14, 0, 8);
            this.tableLayoutPanel1.Controls.Add(this.label15, 2, 8);
            this.tableLayoutPanel1.Controls.Add(this.label16, 0, 10);
            this.tableLayoutPanel1.Controls.Add(this.label17, 2, 10);
            this.tableLayoutPanel1.Controls.Add(this.label18, 0, 12);
            this.tableLayoutPanel1.Controls.Add(this.label19, 0, 13);
            this.tableLayoutPanel1.Controls.Add(this.label20, 0, 15);
            this.tableLayoutPanel1.Controls.Add(this.label21, 0, 16);
            this.tableLayoutPanel1.Controls.Add(this.label22, 0, 18);
            this.tableLayoutPanel1.Controls.Add(this.label23, 0, 19);
            this.tableLayoutPanel1.Controls.Add(this.label24, 0, 20);
            this.tableLayoutPanel1.Controls.Add(this.pickupAddressComboBox, 1, 2);
            this.tableLayoutPanel1.Controls.Add(this.addressTitleTextBox, 1, 4);
            this.tableLayoutPanel1.Controls.Add(this.contactNameTextBox, 1, 5);
            this.tableLayoutPanel1.Controls.Add(this.phone1TextBox, 1, 6);
            this.tableLayoutPanel1.Controls.Add(this.phone2TextBox, 3, 6);
            this.tableLayoutPanel1.Controls.Add(this.flatNoTextBox, 1, 8);
            this.tableLayoutPanel1.Controls.Add(this.buildingNoTextBox, 3, 8);
            this.tableLayoutPanel1.Controls.Add(this.roadTextBox, 1, 10);
            this.tableLayoutPanel1.Controls.Add(this.blockNoTextBox, 3, 10);
            this.tableLayoutPanel1.Controls.Add(this.notesTextBox, 1, 13);
            this.tableLayoutPanel1.Controls.Add(this.collectionAmountTextBox, 1, 19);
            this.tableLayoutPanel1.Controls.Add(this.paymentMethodComboBox, 1, 20);
            this.tableLayoutPanel1.Controls.Add(this.label25, 0, 17);
            this.tableLayoutPanel1.Controls.Add(this.packageTypeComboBox, 1, 15);
            this.tableLayoutPanel1.Controls.Add(this.serviceTypeComboBox, 1, 16);
            this.tableLayoutPanel1.Controls.Add(this.preferredDeliveryTimeComboBox, 1, 17);
            this.tableLayoutPanel1.Controls.Add(this.moneyDeliveryTypeComboBox, 1, 18);
            this.tableLayoutPanel1.Controls.Add(this.locationComboBox, 1, 12);
            this.tableLayoutPanel1.Controls.Add(this.label26, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.clientCreditDataTextBox, 1, 0);
            this.tableLayoutPanel1.Controls.Add(this.serviceTypePriceTextBox, 3, 16);
            this.tableLayoutPanel1.Controls.Add(this.label29, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.label30, 0, 3);
            this.tableLayoutPanel1.Controls.Add(this.label31, 0, 14);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(3, 16);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 22;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.tableLayoutPanel1.Size = new System.Drawing.Size(479, 488);
            this.tableLayoutPanel1.TabIndex = 0;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label9.Location = new System.Drawing.Point(3, 93);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(124, 26);
            this.label9.TabIndex = 0;
            this.label9.Text = "Address Title";
            this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label10.Location = new System.Drawing.Point(3, 46);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(124, 27);
            this.label10.TabIndex = 0;
            this.label10.Text = "Pickup Address";
            this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label11.Location = new System.Drawing.Point(3, 119);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(124, 26);
            this.label11.TabIndex = 0;
            this.label11.Text = "Contact Name";
            this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label12.Location = new System.Drawing.Point(3, 145);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(124, 26);
            this.label12.TabIndex = 0;
            this.label12.Text = "Phone 1";
            this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label13.Location = new System.Drawing.Point(272, 145);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(65, 26);
            this.label13.TabIndex = 0;
            this.label13.Text = "Phone 2";
            this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label14.Location = new System.Drawing.Point(3, 171);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(124, 26);
            this.label14.TabIndex = 0;
            this.label14.Text = "Flat No";
            this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label15.Location = new System.Drawing.Point(272, 171);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(65, 26);
            this.label15.TabIndex = 0;
            this.label15.Text = "Building No";
            this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label16.Location = new System.Drawing.Point(3, 197);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(124, 26);
            this.label16.TabIndex = 0;
            this.label16.Text = "Road";
            this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label17
            // 
            this.label17.AutoSize = true;
            this.label17.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label17.Location = new System.Drawing.Point(272, 197);
            this.label17.Name = "label17";
            this.label17.Size = new System.Drawing.Size(65, 26);
            this.label17.TabIndex = 0;
            this.label17.Text = "Block No";
            this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label18
            // 
            this.label18.AutoSize = true;
            this.label18.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label18.Location = new System.Drawing.Point(3, 223);
            this.label18.Name = "label18";
            this.label18.Size = new System.Drawing.Size(124, 27);
            this.label18.TabIndex = 0;
            this.label18.Text = "Location";
            this.label18.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label19
            // 
            this.label19.AutoSize = true;
            this.label19.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label19.Location = new System.Drawing.Point(3, 250);
            this.label19.Name = "label19";
            this.label19.Size = new System.Drawing.Size(124, 26);
            this.label19.TabIndex = 0;
            this.label19.Text = "Notes";
            this.label19.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label20
            // 
            this.label20.AutoSize = true;
            this.label20.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label20.Location = new System.Drawing.Point(3, 296);
            this.label20.Name = "label20";
            this.label20.Size = new System.Drawing.Size(124, 27);
            this.label20.TabIndex = 0;
            this.label20.Text = "Package Type";
            this.label20.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label21
            // 
            this.label21.AutoSize = true;
            this.label21.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label21.Location = new System.Drawing.Point(3, 323);
            this.label21.Name = "label21";
            this.label21.Size = new System.Drawing.Size(124, 27);
            this.label21.TabIndex = 0;
            this.label21.Text = "Service Type";
            this.label21.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label22
            // 
            this.label22.AutoSize = true;
            this.label22.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label22.Location = new System.Drawing.Point(3, 377);
            this.label22.Name = "label22";
            this.label22.Size = new System.Drawing.Size(124, 27);
            this.label22.TabIndex = 0;
            this.label22.Text = "Money Delivery Type";
            this.label22.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label23
            // 
            this.label23.AutoSize = true;
            this.label23.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label23.Location = new System.Drawing.Point(3, 404);
            this.label23.Name = "label23";
            this.label23.Size = new System.Drawing.Size(124, 26);
            this.label23.TabIndex = 0;
            this.label23.Text = "Collection Amount";
            this.label23.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label24
            // 
            this.label24.AutoSize = true;
            this.label24.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label24.Location = new System.Drawing.Point(3, 430);
            this.label24.Name = "label24";
            this.label24.Size = new System.Drawing.Size(124, 27);
            this.label24.TabIndex = 0;
            this.label24.Text = "Payment Method";
            this.label24.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // pickupAddressComboBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.pickupAddressComboBox, 3);
            this.pickupAddressComboBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.pickupAddressComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.pickupAddressComboBox.FormattingEnabled = true;
            this.pickupAddressComboBox.Location = new System.Drawing.Point(133, 49);
            this.pickupAddressComboBox.Name = "pickupAddressComboBox";
            this.pickupAddressComboBox.Size = new System.Drawing.Size(343, 21);
            this.pickupAddressComboBox.TabIndex = 2;
            this.pickupAddressComboBox.SelectedIndexChanged += new System.EventHandler(this.pickupAddressComboBox_SelectedIndexChanged);
            // 
            // addressTitleTextBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.addressTitleTextBox, 3);
            this.addressTitleTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.addressTitleTextBox.Location = new System.Drawing.Point(133, 96);
            this.addressTitleTextBox.Name = "addressTitleTextBox";
            this.addressTitleTextBox.Size = new System.Drawing.Size(343, 20);
            this.addressTitleTextBox.TabIndex = 3;
            // 
            // contactNameTextBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.contactNameTextBox, 3);
            this.contactNameTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.contactNameTextBox.Location = new System.Drawing.Point(133, 122);
            this.contactNameTextBox.Name = "contactNameTextBox";
            this.contactNameTextBox.Size = new System.Drawing.Size(343, 20);
            this.contactNameTextBox.TabIndex = 4;
            // 
            // phone1TextBox
            // 
            this.phone1TextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.phone1TextBox.Location = new System.Drawing.Point(133, 148);
            this.phone1TextBox.Name = "phone1TextBox";
            this.phone1TextBox.Size = new System.Drawing.Size(133, 20);
            this.phone1TextBox.TabIndex = 5;
            // 
            // phone2TextBox
            // 
            this.phone2TextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.phone2TextBox.Location = new System.Drawing.Point(343, 148);
            this.phone2TextBox.Name = "phone2TextBox";
            this.phone2TextBox.Size = new System.Drawing.Size(133, 20);
            this.phone2TextBox.TabIndex = 6;
            // 
            // flatNoTextBox
            // 
            this.flatNoTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.flatNoTextBox.Location = new System.Drawing.Point(133, 174);
            this.flatNoTextBox.Name = "flatNoTextBox";
            this.flatNoTextBox.Size = new System.Drawing.Size(133, 20);
            this.flatNoTextBox.TabIndex = 7;
            // 
            // buildingNoTextBox
            // 
            this.buildingNoTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.buildingNoTextBox.Location = new System.Drawing.Point(343, 174);
            this.buildingNoTextBox.Name = "buildingNoTextBox";
            this.buildingNoTextBox.Size = new System.Drawing.Size(133, 20);
            this.buildingNoTextBox.TabIndex = 8;
            // 
            // roadTextBox
            // 
            this.roadTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.roadTextBox.Location = new System.Drawing.Point(133, 200);
            this.roadTextBox.Name = "roadTextBox";
            this.roadTextBox.Size = new System.Drawing.Size(133, 20);
            this.roadTextBox.TabIndex = 9;
            // 
            // blockNoTextBox
            // 
            this.blockNoTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.blockNoTextBox.Location = new System.Drawing.Point(343, 200);
            this.blockNoTextBox.Name = "blockNoTextBox";
            this.blockNoTextBox.Size = new System.Drawing.Size(133, 20);
            this.blockNoTextBox.TabIndex = 10;
            this.blockNoTextBox.Validating += new System.ComponentModel.CancelEventHandler(this.blockNoTextBox_Validating);
            // 
            // notesTextBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.notesTextBox, 3);
            this.notesTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.notesTextBox.Location = new System.Drawing.Point(133, 253);
            this.notesTextBox.Name = "notesTextBox";
            this.notesTextBox.Size = new System.Drawing.Size(343, 20);
            this.notesTextBox.TabIndex = 12;
            // 
            // collectionAmountTextBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.collectionAmountTextBox, 3);
            this.collectionAmountTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.collectionAmountTextBox.Location = new System.Drawing.Point(133, 407);
            this.collectionAmountTextBox.Name = "collectionAmountTextBox";
            this.collectionAmountTextBox.Size = new System.Drawing.Size(343, 20);
            this.collectionAmountTextBox.TabIndex = 18;
            // 
            // paymentMethodComboBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.paymentMethodComboBox, 3);
            this.paymentMethodComboBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.paymentMethodComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.paymentMethodComboBox.FormattingEnabled = true;
            this.paymentMethodComboBox.Location = new System.Drawing.Point(133, 433);
            this.paymentMethodComboBox.Name = "paymentMethodComboBox";
            this.paymentMethodComboBox.Size = new System.Drawing.Size(343, 21);
            this.paymentMethodComboBox.TabIndex = 20;
            // 
            // label25
            // 
            this.label25.AutoSize = true;
            this.label25.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label25.Location = new System.Drawing.Point(3, 350);
            this.label25.Name = "label25";
            this.label25.Size = new System.Drawing.Size(124, 27);
            this.label25.TabIndex = 0;
            this.label25.Text = "Preferred Delivery Time";
            this.label25.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // packageTypeComboBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.packageTypeComboBox, 3);
            this.packageTypeComboBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.packageTypeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.packageTypeComboBox.FormattingEnabled = true;
            this.packageTypeComboBox.Location = new System.Drawing.Point(133, 299);
            this.packageTypeComboBox.Name = "packageTypeComboBox";
            this.packageTypeComboBox.Size = new System.Drawing.Size(343, 21);
            this.packageTypeComboBox.TabIndex = 13;
            // 
            // serviceTypeComboBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.serviceTypeComboBox, 2);
            this.serviceTypeComboBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.serviceTypeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.serviceTypeComboBox.FormattingEnabled = true;
            this.serviceTypeComboBox.Location = new System.Drawing.Point(133, 326);
            this.serviceTypeComboBox.Name = "serviceTypeComboBox";
            this.serviceTypeComboBox.Size = new System.Drawing.Size(204, 21);
            this.serviceTypeComboBox.TabIndex = 14;
            this.serviceTypeComboBox.SelectedIndexChanged += new System.EventHandler(this.serviceTypeComboBox_SelectedIndexChanged);
            // 
            // preferredDeliveryTimeComboBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.preferredDeliveryTimeComboBox, 3);
            this.preferredDeliveryTimeComboBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.preferredDeliveryTimeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.preferredDeliveryTimeComboBox.FormattingEnabled = true;
            this.preferredDeliveryTimeComboBox.Location = new System.Drawing.Point(133, 353);
            this.preferredDeliveryTimeComboBox.Name = "preferredDeliveryTimeComboBox";
            this.preferredDeliveryTimeComboBox.Size = new System.Drawing.Size(343, 21);
            this.preferredDeliveryTimeComboBox.TabIndex = 16;
            // 
            // moneyDeliveryTypeComboBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.moneyDeliveryTypeComboBox, 3);
            this.moneyDeliveryTypeComboBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.moneyDeliveryTypeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.moneyDeliveryTypeComboBox.FormattingEnabled = true;
            this.moneyDeliveryTypeComboBox.Location = new System.Drawing.Point(133, 380);
            this.moneyDeliveryTypeComboBox.Name = "moneyDeliveryTypeComboBox";
            this.moneyDeliveryTypeComboBox.Size = new System.Drawing.Size(343, 21);
            this.moneyDeliveryTypeComboBox.TabIndex = 17;
            // 
            // locationComboBox
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.locationComboBox, 3);
            this.locationComboBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.locationComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.locationComboBox.FormattingEnabled = true;
            this.locationComboBox.Location = new System.Drawing.Point(133, 226);
            this.locationComboBox.Name = "locationComboBox";
            this.locationComboBox.Size = new System.Drawing.Size(343, 21);
            this.locationComboBox.TabIndex = 11;
            this.locationComboBox.SelectedIndexChanged += new System.EventHandler(this.locationComboBox_SelectedIndexChanged);
            // 
            // label26
            // 
            this.label26.AutoSize = true;
            this.label26.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label26.Location = new System.Drawing.Point(3, 0);
            this.label26.Name = "label26";
            this.label26.Size = new System.Drawing.Size(124, 26);
            this.label26.TabIndex = 7;
            this.label26.Text = "Client Credits:";
            this.label26.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // clientCreditDataTextBox
            // 
            this.clientCreditDataTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.clientCreditDataTextBox.Enabled = false;
            this.clientCreditDataTextBox.Location = new System.Drawing.Point(133, 3);
            this.clientCreditDataTextBox.Name = "clientCreditDataTextBox";
            this.clientCreditDataTextBox.Size = new System.Drawing.Size(133, 20);
            this.clientCreditDataTextBox.TabIndex = 1;
            // 
            // serviceTypePriceTextBox
            // 
            this.serviceTypePriceTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.serviceTypePriceTextBox.Enabled = false;
            this.serviceTypePriceTextBox.Location = new System.Drawing.Point(343, 326);
            this.serviceTypePriceTextBox.Name = "serviceTypePriceTextBox";
            this.serviceTypePriceTextBox.Size = new System.Drawing.Size(133, 20);
            this.serviceTypePriceTextBox.TabIndex = 15;
            // 
            // label29
            // 
            this.label29.AutoSize = true;
            this.label29.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.tableLayoutPanel1.SetColumnSpan(this.label29, 4);
            this.label29.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label29.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label29.Location = new System.Drawing.Point(3, 26);
            this.label29.Name = "label29";
            this.label29.Size = new System.Drawing.Size(473, 20);
            this.label29.TabIndex = 22;
            this.label29.Text = "Pickup Details:";
            this.label29.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label30
            // 
            this.label30.AutoSize = true;
            this.label30.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.tableLayoutPanel1.SetColumnSpan(this.label30, 4);
            this.label30.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label30.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label30.Location = new System.Drawing.Point(3, 73);
            this.label30.Name = "label30";
            this.label30.Size = new System.Drawing.Size(473, 20);
            this.label30.TabIndex = 22;
            this.label30.Text = "Delivery Details:";
            this.label30.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label31
            // 
            this.label31.AutoSize = true;
            this.label31.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.tableLayoutPanel1.SetColumnSpan(this.label31, 4);
            this.label31.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label31.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label31.Location = new System.Drawing.Point(3, 276);
            this.label31.Name = "label31";
            this.label31.Size = new System.Drawing.Size(473, 20);
            this.label31.TabIndex = 22;
            this.label31.Text = "Package Details:";
            this.label31.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // listInProgressOrdersButton
            // 
            this.listInProgressOrdersButton.Location = new System.Drawing.Point(521, 307);
            this.listInProgressOrdersButton.Name = "listInProgressOrdersButton";
            this.listInProgressOrdersButton.Size = new System.Drawing.Size(126, 23);
            this.listInProgressOrdersButton.TabIndex = 8;
            this.listInProgressOrdersButton.Text = "List In Progress Orders";
            this.listInProgressOrdersButton.UseVisualStyleBackColor = true;
            this.listInProgressOrdersButton.Click += new System.EventHandler(this.listInProgressOrdersButton_Click);
            // 
            // listPendingOrders
            // 
            this.listPendingOrders.Location = new System.Drawing.Point(649, 307);
            this.listPendingOrders.Name = "listPendingOrders";
            this.listPendingOrders.Size = new System.Drawing.Size(114, 23);
            this.listPendingOrders.TabIndex = 8;
            this.listPendingOrders.Text = "List Pending Orders";
            this.listPendingOrders.UseVisualStyleBackColor = true;
            this.listPendingOrders.Click += new System.EventHandler(this.listPendingOrders_Click);
            // 
            // ordersListGridView
            // 
            this.ordersListGridView.AllowUserToAddRows = false;
            this.ordersListGridView.AllowUserToDeleteRows = false;
            this.ordersListGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.ordersListGridView.Location = new System.Drawing.Point(521, 359);
            this.ordersListGridView.Name = "ordersListGridView";
            this.ordersListGridView.ReadOnly = true;
            this.ordersListGridView.Size = new System.Drawing.Size(434, 115);
            this.ordersListGridView.TabIndex = 3;
            // 
            // listClosedOrdersButton
            // 
            this.listClosedOrdersButton.Location = new System.Drawing.Point(769, 307);
            this.listClosedOrdersButton.Name = "listClosedOrdersButton";
            this.listClosedOrdersButton.Size = new System.Drawing.Size(103, 23);
            this.listClosedOrdersButton.TabIndex = 8;
            this.listClosedOrdersButton.Text = "List Closed Orders";
            this.listClosedOrdersButton.UseVisualStyleBackColor = true;
            this.listClosedOrdersButton.Click += new System.EventHandler(this.listClosedOrdersButton_Click);
            // 
            // listReturnedMoneyOrdersButton
            // 
            this.listReturnedMoneyOrdersButton.Location = new System.Drawing.Point(521, 333);
            this.listReturnedMoneyOrdersButton.Name = "listReturnedMoneyOrdersButton";
            this.listReturnedMoneyOrdersButton.Size = new System.Drawing.Size(126, 23);
            this.listReturnedMoneyOrdersButton.TabIndex = 8;
            this.listReturnedMoneyOrdersButton.Text = "List Returned Money Orders";
            this.listReturnedMoneyOrdersButton.UseVisualStyleBackColor = true;
            this.listReturnedMoneyOrdersButton.Click += new System.EventHandler(this.listReturnedMoneyOrdersButton_Click);
            // 
            // getMyInformationButton
            // 
            this.getMyInformationButton.Location = new System.Drawing.Point(523, 490);
            this.getMyInformationButton.Name = "getMyInformationButton";
            this.getMyInformationButton.Size = new System.Drawing.Size(126, 23);
            this.getMyInformationButton.TabIndex = 8;
            this.getMyInformationButton.Text = "Get My Information";
            this.getMyInformationButton.UseVisualStyleBackColor = true;
            this.getMyInformationButton.Click += new System.EventHandler(this.getMyInformationButton_Click);
            // 
            // getCashHistoryButton
            // 
            this.getCashHistoryButton.Location = new System.Drawing.Point(656, 492);
            this.getCashHistoryButton.Name = "getCashHistoryButton";
            this.getCashHistoryButton.Size = new System.Drawing.Size(126, 23);
            this.getCashHistoryButton.TabIndex = 8;
            this.getCashHistoryButton.Text = "Get Cash History";
            this.getCashHistoryButton.UseVisualStyleBackColor = true;
            this.getCashHistoryButton.Click += new System.EventHandler(this.getCashHistoryButton_Click);
            // 
            // responseOutputTextBox
            // 
            this.responseOutputTextBox.Location = new System.Drawing.Point(521, 577);
            this.responseOutputTextBox.Multiline = true;
            this.responseOutputTextBox.Name = "responseOutputTextBox";
            this.responseOutputTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.responseOutputTextBox.Size = new System.Drawing.Size(434, 104);
            this.responseOutputTextBox.TabIndex = 9;
            // 
            // getCashOnTheWayButton
            // 
            this.getCashOnTheWayButton.Location = new System.Drawing.Point(788, 490);
            this.getCashOnTheWayButton.Name = "getCashOnTheWayButton";
            this.getCashOnTheWayButton.Size = new System.Drawing.Size(126, 23);
            this.getCashOnTheWayButton.TabIndex = 8;
            this.getCashOnTheWayButton.Text = "Get Cash On The Way";
            this.getCashOnTheWayButton.UseVisualStyleBackColor = true;
            this.getCashOnTheWayButton.Click += new System.EventHandler(this.getCashOnTheWayButton_Click);
            // 
            // addClientDeviceButton
            // 
            this.addClientDeviceButton.Location = new System.Drawing.Point(716, 523);
            this.addClientDeviceButton.Name = "addClientDeviceButton";
            this.addClientDeviceButton.Size = new System.Drawing.Size(116, 23);
            this.addClientDeviceButton.TabIndex = 10;
            this.addClientDeviceButton.Text = "Add Client Device";
            this.addClientDeviceButton.UseVisualStyleBackColor = true;
            this.addClientDeviceButton.Click += new System.EventHandler(this.addClientDeviceButton_Click);
            // 
            // deleteClientDeviceButton
            // 
            this.deleteClientDeviceButton.Location = new System.Drawing.Point(838, 522);
            this.deleteClientDeviceButton.Name = "deleteClientDeviceButton";
            this.deleteClientDeviceButton.Size = new System.Drawing.Size(121, 23);
            this.deleteClientDeviceButton.TabIndex = 10;
            this.deleteClientDeviceButton.Text = "Delete Client Device";
            this.deleteClientDeviceButton.UseVisualStyleBackColor = true;
            this.deleteClientDeviceButton.Click += new System.EventHandler(this.removeClientDeviceButton_Click);
            // 
            // clientDeviceIDTextBox
            // 
            this.clientDeviceIDTextBox.Location = new System.Drawing.Point(583, 524);
            this.clientDeviceIDTextBox.Name = "clientDeviceIDTextBox";
            this.clientDeviceIDTextBox.Size = new System.Drawing.Size(127, 20);
            this.clientDeviceIDTextBox.TabIndex = 11;
            // 
            // label33
            // 
            this.label33.AutoSize = true;
            this.label33.Location = new System.Drawing.Point(522, 527);
            this.label33.Name = "label33";
            this.label33.Size = new System.Drawing.Size(55, 13);
            this.label33.TabIndex = 12;
            this.label33.Text = "Device ID";
            // 
            // validateLoginButton
            // 
            this.validateLoginButton.Location = new System.Drawing.Point(526, 551);
            this.validateLoginButton.Name = "validateLoginButton";
            this.validateLoginButton.Size = new System.Drawing.Size(121, 23);
            this.validateLoginButton.TabIndex = 10;
            this.validateLoginButton.Text = "ValidateLogin";
            this.validateLoginButton.UseVisualStyleBackColor = true;
            this.validateLoginButton.Click += new System.EventHandler(this.validateLoginButton_Click);
            // 
            // APITesterForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(974, 716);
            this.Controls.Add(this.label33);
            this.Controls.Add(this.clientDeviceIDTextBox);
            this.Controls.Add(this.validateLoginButton);
            this.Controls.Add(this.deleteClientDeviceButton);
            this.Controls.Add(this.addClientDeviceButton);
            this.Controls.Add(this.responseOutputTextBox);
            this.Controls.Add(this.getCashOnTheWayButton);
            this.Controls.Add(this.getCashHistoryButton);
            this.Controls.Add(this.getMyInformationButton);
            this.Controls.Add(this.listReturnedMoneyOrdersButton);
            this.Controls.Add(this.listClosedOrdersButton);
            this.Controls.Add(this.listPendingOrders);
            this.Controls.Add(this.listInProgressOrdersButton);
            this.Controls.Add(this.groupBox6);
            this.Controls.Add(this.groupBox5);
            this.Controls.Add(this.ordersListGridView);
            this.Controls.Add(this.groupBox2);
            this.Name = "APITesterForm";
            this.Text = "Fast Bird API Tester";
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.statusHistoryGridView)).EndInit();
            this.groupBox5.ResumeLayout(false);
            this.groupBox5.PerformLayout();
            this.groupBox6.ResumeLayout(false);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.ordersListGridView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox aPIUrlTextBox;
        private System.Windows.Forms.Button createOrderButton;
        private System.Windows.Forms.Button getOrderStatusButton;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox fbdNumberTextBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox orderWSTextBox;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox orderTrackingWSTextBox;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox mastersWSTextBox;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox clientsWSTextBox;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button getOrderApprovalStatusButton;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox usernameTextBox;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox passwordTextBox;
        private System.Windows.Forms.GroupBox groupBox5;
        private System.Windows.Forms.Button loadInitDataButton;
        private System.Windows.Forms.GroupBox groupBox6;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.Label label17;
        private System.Windows.Forms.Label label18;
        private System.Windows.Forms.Label label19;
        private System.Windows.Forms.Label label20;
        private System.Windows.Forms.Label label21;
        private System.Windows.Forms.Label label22;
        private System.Windows.Forms.Label label23;
        private System.Windows.Forms.Label label24;
        private System.Windows.Forms.ComboBox pickupAddressComboBox;
        private System.Windows.Forms.TextBox addressTitleTextBox;
        private System.Windows.Forms.TextBox contactNameTextBox;
        private System.Windows.Forms.TextBox phone1TextBox;
        private System.Windows.Forms.TextBox phone2TextBox;
        private System.Windows.Forms.TextBox flatNoTextBox;
        private System.Windows.Forms.TextBox buildingNoTextBox;
        private System.Windows.Forms.TextBox roadTextBox;
        private System.Windows.Forms.TextBox blockNoTextBox;
        private System.Windows.Forms.TextBox notesTextBox;
        private System.Windows.Forms.TextBox collectionAmountTextBox;
        private System.Windows.Forms.ComboBox paymentMethodComboBox;
        private System.Windows.Forms.Label label25;
        private System.Windows.Forms.ComboBox packageTypeComboBox;
        private System.Windows.Forms.ComboBox serviceTypeComboBox;
        private System.Windows.Forms.ComboBox preferredDeliveryTimeComboBox;
        private System.Windows.Forms.ComboBox moneyDeliveryTypeComboBox;
        private System.Windows.Forms.ComboBox locationComboBox;
        private System.Windows.Forms.Label label26;
        private System.Windows.Forms.TextBox clientCreditDataTextBox;
        private System.Windows.Forms.TextBox serviceTypePriceTextBox;
        private System.Windows.Forms.DataGridView statusHistoryGridView;
        private System.Windows.Forms.TextBox approvalStatusTextBox;
        private System.Windows.Forms.TextBox statusTextBox;
        private System.Windows.Forms.Label label28;
        private System.Windows.Forms.Label label27;
        private System.Windows.Forms.Label label29;
        private System.Windows.Forms.Label label30;
        private System.Windows.Forms.Label label31;
        private System.Windows.Forms.Button listInProgressOrdersButton;
        private System.Windows.Forms.Button listPendingOrders;
        private System.Windows.Forms.DataGridView ordersListGridView;
        private System.Windows.Forms.Button listClosedOrdersButton;
        private System.Windows.Forms.Button listReturnedMoneyOrdersButton;
        private System.Windows.Forms.Button getMyInformationButton;
        private System.Windows.Forms.TextBox moneyCollectionWSTextBox;
        private System.Windows.Forms.Label label32;
        private System.Windows.Forms.Button getCashHistoryButton;
        private System.Windows.Forms.TextBox responseOutputTextBox;
        private System.Windows.Forms.Button getCashOnTheWayButton;
        private System.Windows.Forms.Button addClientDeviceButton;
        private System.Windows.Forms.Button deleteClientDeviceButton;
        private System.Windows.Forms.TextBox clientDeviceIDTextBox;
        private System.Windows.Forms.Label label33;
        private System.Windows.Forms.Button validateLoginButton;
    }
}

