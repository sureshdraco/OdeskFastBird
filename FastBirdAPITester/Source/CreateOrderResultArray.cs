using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;
using FastBirdAPITester;

namespace GoldenHills.FastBird.ClientArea.Web.API
{
    [DataContract]
    public class CreateOrderResultArray
    {
        
        [DataMember]
        public string[] d { get; set; }
        
        
    }

    public class ListServiceTypeResult
    {
        public Dictionary<Guid,string> d { get; set; }
    }
}