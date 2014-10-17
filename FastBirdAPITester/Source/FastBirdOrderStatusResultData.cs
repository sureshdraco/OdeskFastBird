using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

public class FastBirdOrderStatusResultData
{
    public string Error { get; set; }
    public string CurrentStatus { get; set; }
    public List<FastBirdOrderHistoryLine> History { get; set; }
}
