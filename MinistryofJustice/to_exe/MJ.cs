using System;
using System.Collections.Generic;
using System.Linq;
using System.Management;
using System.Text;
using System.Threading.Tasks;

namespace MJ
{
    public class HardWhere
    {
        public string CPU_NAME()
        {
            string cpu_name = "";
            ManagementObjectSearcher mos = new ManagementObjectSearcher("root\\CIMV2", "SELECT * FROM Win32_Processor");
            foreach (ManagementObject mo in mos.Get())
            {
                if (mo["Name"] == null)
                {
                    cpu_name = "None";
                }
                else
                {
                    cpu_name = mo["Name"].ToString();
                }
            }

            return cpu_name;
        }

        public string HDD_SERIAL()
        {
            string hdd_serial = "";
            ManagementObjectSearcher mos = new ManagementObjectSearcher("SELECT * FROM Win32_PhysicalMedia");
            foreach (ManagementObject mo in mos.Get())
            {
                if (mo["SerialNumber"] == null)
                {
                    hdd_serial = "None";
                }
                else
                {
                    hdd_serial = mo["SerialNumber"].ToString();
                }
            }
            return hdd_serial;
        }
    }
}
