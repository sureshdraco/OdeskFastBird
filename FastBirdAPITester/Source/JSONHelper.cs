using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization.Json;
using System.IO;
using System.Text;
/// <summary>
/// Summary description for JASONHelper
/// </summary>


internal class JSONHelper
{
    public static string Serialize<T>(T obj)
    {
        DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
        MemoryStream ms = new MemoryStream();
        serializer.WriteObject(ms, obj);
        string retVal = Encoding.Default.GetString(ms.ToArray());
        ms.Dispose();
        return retVal;
    }

    public static T Deserialize<T>(string json)
    {
        T obj = Activator.CreateInstance<T>();
        MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(json));
        DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
        
        obj = (T)serializer.ReadObject(ms);
        ms.Close();
        ms.Dispose();
        return obj;
    }
}

