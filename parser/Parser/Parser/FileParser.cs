using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml;

namespace Parser
{
    public class FileParser
    {
        public void Parse(string filename)
        {
            XmlDocument document = new XmlDocument();
            document.Load(filename);
            XmlNodeList channels = document.SelectNodes("//channel/display-name[1]");
foreach (XmlNode item in channels)
            {
                Console.WriteLine(item.InnerText);
            }
        }
    }
}
