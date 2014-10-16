using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace GoldenHills.FastBird.ClientArea.Web.API
{
    [DataContract]
    public class CreateOrderResult
    {
        
        [DataMember]
        public string fbdnumber { get; set; }
        [DataMember]
        public string error { get; set; }
        
    }
}