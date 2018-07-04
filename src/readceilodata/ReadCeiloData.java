/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package readceilodata;

/**
 *
 * @author Szabó-Takács Beáta
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import ucar.ma2.*;
import ucar.nc2.*;
import java.util.List;
import java.lang.*;
public class ReadCeiloData{ 

    /**
     * @param args the command line arguments
     */
   
    public static void reader(String f, String location)throws IOException, InvalidRangeException {
        BufferedReader br = null;
        FileReader fr = null;
        String s ="";
        String sCurrentLine ="";
        String tim;
        String cloud;
        String scline;
        String info;
        String version = System.getProperty("java.version");
        int c_layer_1;
        int c_layer_2;
        int c_layer_3;
        int c_layer_4;
        int c_layer_5;
        int layer_1;
        int layer_2;
        int layer_3;
        int layer_4;
        int layer_5;
        int fstdigit;
        String scdigit;
        int LCB;
        int SCB;
        int HCB;
        char[] AWS;
        String hexa;
        int hei;
        int sc2;
        int re;
        int prof;
        int le;
        String temp;
        int wt;
        int ti;
        int bcl;
        String par;
        int sbac;
        String hx3;
        StringBuffer pr = new StringBuffer();
       
       
        int i=0;
        
        NetcdfFileWriter ncfile = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf4, location, null);
        Dimension heightDim = ncfile.addDimension(null,"height",1539);
        Dimension timeDim = ncfile.addUnlimitedDimension("time");
        
        Variable height = ncfile.addVariable(null,"height",DataType.INT,"height");
        height.addAttribute(new Attribute("long_name","height"));
        height.addAttribute(new Attribute("units","m"));
        Variable time = ncfile.addVariable(null, "time", DataType.DOUBLE, "time");
        time.addAttribute( new Attribute("units", "days since 1970-01-01"));
        Variable cloudL1 = ncfile.addVariable(null, "cloudL1", DataType.INT, "time");
        cloudL1.addAttribute( new Attribute("long_name", "cloud amount in 1st layer1"));
        cloudL1.addAttribute( new Attribute("units", "octa"));
        cloudL1.addAttribute(new Attribute("_FillValue",-9999));
        cloudL1.addAttribute(new Attribute("Vertical Visibility",9));
        cloudL1.addAttribute(new Attribute("No enough data",99));
        Variable cLayer1 = ncfile.addVariable(null,"cLayer1",DataType.INT,"time");
        cLayer1.addAttribute(new Attribute("long_name", "1st cloud layer height"));
        cLayer1.addAttribute(new Attribute("units","meter"));
        cLayer1.addAttribute(new Attribute("_FillValue",-9999));
        Variable cloudL2 = ncfile.addVariable(null, "cloudL2", DataType.INT, "time");
        cloudL2.addAttribute( new Attribute("long_name", "cloud amount in 2nd layer"));
        cloudL2.addAttribute( new Attribute("units", "octa"));
        cloudL2.addAttribute(new Attribute("_FillValue",-9999));
        cloudL2.addAttribute(new Attribute("Vertical Visibility",9));
        cloudL2.addAttribute(new Attribute("No enough data",99));
        Variable cLayer2 = ncfile.addVariable(null,"cLayer2",DataType.INT,"time");
        cLayer2.addAttribute(new Attribute("long_name", "2nd cloud layer height"));
        cLayer2.addAttribute(new Attribute("units","meter"));
        cLayer2.addAttribute(new Attribute("_FillValue",-9999));
        Variable cloudL3 = ncfile.addVariable(null, "cloudL3", DataType.INT, "time");
        cloudL3.addAttribute( new Attribute("long_name", "cloud amount in 3th layer"));
        cloudL3.addAttribute( new Attribute("units", "octa"));
        cloudL3.addAttribute(new Attribute("_FillValue",-9999));
        cloudL3.addAttribute(new Attribute("Vertical Visibility",9));
        cloudL3.addAttribute(new Attribute("No enough data",99));
        Variable cLayer3 = ncfile.addVariable(null,"cLayer3",DataType.INT,"time");
        cLayer3.addAttribute(new Attribute("long_name", "3th cloud layer height"));
        cLayer3.addAttribute(new Attribute("units","meter"));
        cLayer3.addAttribute(new Attribute("Fill_value",-9999));
        Variable cloudL4 = ncfile.addVariable(null, "cloudL4", DataType.INT, "time");
        cloudL4.addAttribute( new Attribute("long_name", "cloud amount in 4th layer"));
        cloudL4.addAttribute( new Attribute("units", "octa"));
        cloudL4.addAttribute(new Attribute("_FillValue",-9999));
        cloudL4.addAttribute(new Attribute("Vertical Visibility", 9));
        cloudL4.addAttribute(new Attribute("No enough data",99));
        Variable cLayer4 = ncfile.addVariable(null,"cLayer4",DataType.INT,"time");
        cLayer4.addAttribute(new Attribute("long_name", "4th cloud layer height"));
        cLayer4.addAttribute(new Attribute("units","meter"));
        cLayer4.addAttribute(new Attribute("_FillValue",-9999));
        Variable cloudL5 = ncfile.addVariable(null, "cloudL5", DataType.INT, "time");
        cloudL5.addAttribute( new Attribute("long_name", "cloud amount in 5th layer"));
        cloudL5.addAttribute( new Attribute("units", "octa"));
        cloudL5.addAttribute(new Attribute("_FillValue",-9999));
        cloudL5.addAttribute(new Attribute("Vertical Visibility",9));
        cloudL5.addAttribute(new Attribute("no enough data",99));
        Variable cLayer5 = ncfile.addVariable(null,"cLayer5",DataType.INT,"time");
        cLayer5.addAttribute(new Attribute("long_name", "5th cloud layer height"));
        cLayer5.addAttribute(new Attribute("units","meter"));
        cLayer5.addAttribute(new Attribute("_FillValue",-9999));
        Variable CBN = ncfile.addVariable(null,"CB",DataType.INT,"time");
        CBN.addAttribute(new Attribute("long_name","cloud base number"));
        CBN.addAttribute(new Attribute("units", "number"));
        CBN.addAttribute(new Attribute("no significant backscatter",0));
        CBN.addAttribute(new Attribute("obscuration but no cloud",4));
        CBN.addAttribute(new Attribute("transparent obscuration",5));
        CBN.addAttribute(new Attribute("_FillValue",-9));
        Variable Self_check = ncfile.addVariable(null,"shelf_check",DataType.CHAR,"time");
        Self_check.addAttribute(new Attribute("OK","0"));
        Self_check.addAttribute(new Attribute("Warning","W"));
        Self_check.addAttribute(new Attribute("Alarm","A"));
        Variable LBCH = ncfile.addVariable(null,"LBCH",DataType.INT,"time");
        LBCH.addAttribute(new Attribute("long_name","Lowest cloud base height"));
        LBCH.addAttribute(new Attribute("units","meter"));
        LBCH.addAttribute(new Attribute("not detected",-99000));
        Variable VV = ncfile.addVariable(null,"VV",DataType.INT,"time");
        VV.addAttribute(new Attribute("long_name","Vertical visibility as calculated"));
        VV.addAttribute(new Attribute("units","meter"));
        VV.addAttribute(new Attribute("not detected",-99000));
        Variable SCBH = ncfile.addVariable(null,"SCBH",DataType.INT,"time");
        SCBH.addAttribute(new Attribute("long_name","Second lowest cloud base height"));
        SCBH.addAttribute(new Attribute("units","meter"));
        SCBH.addAttribute(new Attribute("not detected",-99000));
        Variable HCBH = ncfile.addVariable(null,"HCBH",DataType.INT,"time");
        HCBH.addAttribute(new Attribute("long_name","Highest cloud base height"));
        HCBH.addAttribute(new Attribute("units","meter"));
        HCBH.addAttribute(new Attribute("not detected",-99000));
        Variable AWSI = ncfile.addVariable(null,"AWSI",DataType.STRING,"time");
        AWSI.addAttribute(new Attribute("long_name","Alarm Warning Internal Status information"));
        Variable TWABP = ncfile.addVariable(null,"TWABP",DataType.DOUBLE,"time height");
        TWABP.addAttribute(new Attribute("long_name","two-way attenuated backscatter profile"));
        TWABP.addAttribute(new Attribute("units","(str km)-1"));
        Variable Scale = ncfile.addVariable(null,"Scale",DataType.INT,"time");
        Scale.addAttribute(new Attribute("long name","Scale Parameter"));
        Scale.addAttribute(new Attribute("units","%"));
        Variable BPR = ncfile.addVariable(null,"BPR",DataType.INT,"time");
        BPR.addAttribute(new Attribute("long name","Backscatter Profile Resolution"));
        BPR.addAttribute(new Attribute("units","meter"));
        Variable LP = ncfile.addVariable(null,"LP",DataType.INT,"time");
        LP.addAttribute(new Attribute("long name","Length of the Profile"));
        Variable LPE = ncfile.addVariable(null,"LPE",DataType.INT,"time");
        LPE.addAttribute(new Attribute("long name","Laser Pulse Energy"));
        LPE.addAttribute(new Attribute("units","%"));
        Variable LT = ncfile.addVariable(null,"LT",DataType.STRING,"time");
        LT.addAttribute(new Attribute("long name","Laser Temperature"));
        LT.addAttribute(new Attribute("units","degree Celsius"));
        Variable WTE = ncfile.addVariable(null,"WTE",DataType.INT,"time");
        WTE.addAttribute(new Attribute("long name","Window Transmission Estimate"));
        WTE.addAttribute(new Attribute("units","%"));
        Variable Tilt = ncfile.addVariable(null,"Tilt",DataType.INT,"time");
        Tilt.addAttribute(new Attribute("long name","Tilt Angle"));
        Tilt.addAttribute(new Attribute("units","degree from vertical"));
        Variable BCL = ncfile.addVariable(null,"BCL",DataType.INT,"time");
        BCL.addAttribute(new Attribute("long name","Backgroung light"));
        BCL.addAttribute(new Attribute("units","millivolts"));
        Variable MP = ncfile.addVariable(null,"MP",DataType.STRING,"time");
        MP.addAttribute(new Attribute("long name","Measurement Parameters"));
        Variable SDNB = ncfile.addVariable(null,"SDNB",DataType.INT,"time");
        SDNB.addAttribute(new Attribute("long name","Sum of detected normalized backscatter"));



        // add global attributes
        ncfile.addGroupAttribute(null,new Attribute("title","Vaisala CL51 parameters"));
        ncfile.addGroupAttribute(null,new Attribute("nc_created","JDK" + version));
        

        // create the file
        ncfile.create();


        try{
            fr = new FileReader(f);
            br = new BufferedReader(fr);

            int countLinesResult = countLines(f);
            
            ArrayDouble.D1 timeData = new ArrayDouble.D1(countLinesResult-2);
            ArrayInt.D1 heightData = new ArrayInt.D1(heightDim.getLength());
            ArrayInt.D1 cloudL1Data = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 cloudL2Data = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 cloudL3Data = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 cloudL4Data = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 cloudL5Data = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 cLayer1Data = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 cLayer2Data = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 cLayer3Data = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 cLayer4Data = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 cLayer5Data = new ArrayInt.D1(countLinesResult-2);
            Index ima = cloudL1Data.getIndex();
            ArrayInt.D1 CBNData = new ArrayInt.D1(countLinesResult-2);
            ArrayChar.D1 Self_checkData = new ArrayChar.D1(countLinesResult-2);
            ArrayInt.D1 LBCHData = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 VVData = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 SCBHData = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 HCBHData = new ArrayInt.D1(countLinesResult-2);
            ArrayString.D1 AWSIData = new ArrayString.D1(countLinesResult-2);
            ArrayDouble.D2 TWAB = new ArrayDouble.D2(countLinesResult-2,heightDim.getLength());
            ArrayInt.D1 scaleData = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 bcprofData = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 proflData = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 laserpData = new ArrayInt.D1(countLinesResult-2);
            ArrayString.D1 lasertData = new ArrayString.D1(countLinesResult-2);
            ArrayInt.D1 wtransData = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 tiltData = new ArrayInt.D1(countLinesResult-2);
            ArrayInt.D1 bglightData = new ArrayInt.D1(countLinesResult-2);
            ArrayString.D1 mparametersData = new ArrayString.D1(countLinesResult-2);
            ArrayInt.D1 SumData = new ArrayInt.D1(countLinesResult-2);

            Index ima1 = scaleData.getIndex();
            Index ima2 = TWAB.getIndex();
            Index hi = heightData.getIndex();

            ArrayList<Double> tdate = new ArrayList<Double>();
            ArrayList<Integer> cloud_layer_1 = new ArrayList<Integer>();
            ArrayList<Integer> cloud_layer_2 = new ArrayList<Integer>();
            ArrayList<Integer> cloud_layer_3 = new ArrayList<Integer>();
            ArrayList<Integer> cloud_layer_4 = new ArrayList<Integer>();
            ArrayList<Integer> cloud_layer_5 = new ArrayList<Integer>();
            ArrayList<Integer> LayerC_1 = new ArrayList<Integer>();
            ArrayList<Integer> LayerC_2 = new ArrayList<Integer>();
            ArrayList<Integer> LayerC_3 = new ArrayList<Integer>();
            ArrayList<Integer> LayerC_4 = new ArrayList<Integer>();
            ArrayList<Integer> LayerC_5 = new ArrayList<Integer>();
            ArrayList<Integer> ncb = new ArrayList<Integer>();
            ArrayList<Character>  sc = new ArrayList<Character>();
            ArrayList<Integer> hlbc = new ArrayList<Integer>();
            ArrayList<Integer> hvv = new ArrayList<Integer>();
            ArrayList<Integer> hslbc = new ArrayList<Integer>();
            ArrayList<Integer> hhbc = new ArrayList<Integer>();
            ArrayList<String> iaws = new ArrayList<String>();
            List<Double> hx = new ArrayList<Double>();
            List<Double> hx2 = new ArrayList<Double>();
           // List<Double> hxd = new ArrayList<Double>();
            List<List<Double>> hx2D = new ArrayList<List<Double>>();
            ArrayList<Integer> h = new ArrayList<Integer>();
            ArrayList<Integer> scL = new ArrayList<Integer>();
            ArrayList<Integer> reL = new ArrayList<Integer>();
            ArrayList<Integer> profL = new ArrayList<Integer>();
            ArrayList<Integer> leL = new ArrayList<Integer>();
            ArrayList<String> tempL = new ArrayList<String>();
            ArrayList<Integer> wtL = new ArrayList<Integer>();
            ArrayList<Integer> tiltL = new ArrayList<Integer>();
            ArrayList<Integer> bclL = new ArrayList<Integer>();
            ArrayList<String> parL = new ArrayList<String>();
            ArrayList<Integer> sbacL = new ArrayList<Integer>();

            for(int n = 1; n < 1540; n++){
                hei = n * 10;
                h.add(hei);
            }

            while ((sCurrentLine = br.readLine()) != null) {
               
                
                if (i > 1) {
                    
                       
                    s = sCurrentLine.substring(0, sCurrentLine.indexOf(",")) + "\\n ";
                    tim = sCurrentLine.substring(0, sCurrentLine.indexOf(","));
                    cloud = sCurrentLine.substring(sCurrentLine.indexOf("\\n", sCurrentLine.indexOf("\\n")) + 4);
                    scline = sCurrentLine.substring(sCurrentLine.indexOf("\\n", sCurrentLine.indexOf("\\n")) + 4);
                    hexa = sCurrentLine.substring(sCurrentLine.indexOf("\\n", sCurrentLine.indexOf("\\n")) + 4);
                    info = sCurrentLine.substring(sCurrentLine.indexOf("\\n", sCurrentLine.indexOf("\\n")) + 4);
                    info = info.substring(info.indexOf("\\n", info.indexOf("\\n")) + 4);
                    info = info.substring(info.indexOf("\\n", info.indexOf("\\n")) + 4);
                    info = info.substring(0, info.indexOf("\\n") -2);
                    String sc1 = info.substring(0,3);
                    sc2 = Integer.parseInt(info.substring(0,3));
                    re = Integer.parseInt(info.substring(4,6));
                    prof = Integer.parseInt(info.substring(7,11));
                    le = Integer.parseInt(info.substring(12,15));
                    temp = info.substring(16,19);
                    wt = Integer.parseInt(info.substring(20,23));
                    ti = Integer.parseInt(info.substring(24,26));
                    bcl = Integer.parseInt(info.substring(27,31));
                    par = info.substring(32,41);
                    sbac = Integer.parseInt(info.substring(42,45));
                    hexa = hexa.substring(hexa.indexOf("\\n", hexa.indexOf("\\n")) + 4);
                    hexa = hexa.substring(hexa.indexOf("\\n", hexa.indexOf("\\n")) + 4);
                    hexa = hexa.substring(hexa.indexOf("\\n", hexa.indexOf("\\n")) + 2);
                    hexa = hexa.substring(0, hexa.indexOf("\\n") - 2);
                    scline = scline.substring(0, scline.indexOf("\\n") - 2) + "\\n";
                    fstdigit = Integer.parseInt(scline.substring(0, 1).replace("/", "-9"));
                    scdigit = scline.substring(1, 2);
                    char scdigit2 = scdigit.charAt(0);
                    LCB = Integer.parseInt(scline.substring(3, 8).replace("/////", "-99000"));
                    SCB = Integer.parseInt(scline.substring(9, 14).replace("/////", "-99000"));
                    HCB = Integer.parseInt(scline.substring(15, 20).replace("/////", "-99000"));
                    String AWS1 = scline.substring(21, 33);
                    AWS = AWS1.toCharArray();
                    hx = backscatter(hexa);
                    cloud = cloud.substring(cloud.indexOf("\\n", cloud.indexOf("\\n")) + 4);
                    cloud = cloud.substring(0, cloud.indexOf("\\n") - 2) + "\\n";
                    cloud = cloud.replace("////", "-990");
                    cloud = cloud.replace("-1", "-9999");
                    c_layer_1 = Integer.parseInt(cloud.substring(0, 1));
                    c_layer_2 = Integer.parseInt(cloud.substring(8, 9));
                    c_layer_3 = Integer.parseInt(cloud.substring(16, 17));
                    c_layer_4 = Integer.parseInt(cloud.substring(24, 25));
                    c_layer_5 = Integer.parseInt(cloud.substring(32, 33));
                    layer_1 = Integer.parseInt(cloud.substring(2, 6));
                    layer_2 = Integer.parseInt(cloud.substring(10, 14));
                    layer_3 = Integer.parseInt(cloud.substring(18, 22));
                    layer_4 = Integer.parseInt(cloud.substring(26, 30));
                    layer_5 = Integer.parseInt(cloud.substring(34, 38));
                    Date date2 = dateConv1(s);
                    double dateU = ToMATLABDate(date2);
               
                    //Store the data from line to line into ArrayList

                    tdate.add(dateU);
                    cloud_layer_1.add(c_layer_1);
                    cloud_layer_2.add(c_layer_2);
                    cloud_layer_3.add(c_layer_3);
                    cloud_layer_4.add(c_layer_4);
                    cloud_layer_5.add(c_layer_5);
                    LayerC_1.add(layer_1 * 10);
                    LayerC_2.add(layer_2 * 10);
                    LayerC_3.add(layer_3 * 10);
                    LayerC_4.add(layer_4 * 10);
                    LayerC_5.add(layer_5 * 10);

                    if (fstdigit == 1 || fstdigit == 2 || fstdigit == 3) {
                        ncb.add(fstdigit);
                        hlbc.add(LCB);
                    } else {
                        ncb.add(-99000);
                        hlbc.add(-99000);
                    }
                    sc.add(scdigit2);
                    if (fstdigit == 4) {
                        hvv.add(LCB);
                    } else {
                        hvv.add(-99000);
                    }
                    if (fstdigit == 2 || fstdigit == 3) {
                        hslbc.add(SCB);
                    } else {
                        hslbc.add(-99000);
                    }
                    if (fstdigit == 3) {
                        hhbc.add(HCB);
                    } else {
                        hhbc.add(-99000);
                    }

                    iaws.add(AWS1);
                    hx2D.add(hx);
                    scL.add(sc2);
                    reL.add(re);
                    profL.add(prof);
                    leL.add(le);
                    tempL.add(temp);
                    wtL.add(wt);
                    tiltL.add(ti);
                    bclL.add(bcl);
                    parL.add(par);
                    sbacL.add(sbac);
                   

                }
            i = i + 1 ;

            }

            
            //Store the netCDF variables
            for (int timeIdx = 0; timeIdx < countLinesResult-2;timeIdx++) {
                

                timeData.setDouble(timeIdx,tdate.get(timeIdx));
                cloudL1Data.setInt(ima.set(timeIdx), cloud_layer_1.get(timeIdx));
                cLayer1Data.setInt(ima.set(timeIdx), LayerC_1.get(timeIdx));
                cloudL2Data.setInt(ima.set(timeIdx), cloud_layer_2.get(timeIdx));
                cLayer2Data.setInt(ima.set(timeIdx), LayerC_2.get(timeIdx));
                cloudL3Data.setInt(ima.set(timeIdx), cloud_layer_3.get(timeIdx));
                cLayer3Data.setInt(ima.set(timeIdx), LayerC_3.get(timeIdx));
                cloudL4Data.setInt(ima.set(timeIdx), cloud_layer_4.get(timeIdx));
                cLayer4Data.setInt(ima.set(timeIdx), LayerC_4.get(timeIdx));
                cloudL5Data.setInt(ima.set(timeIdx), cloud_layer_5.get(timeIdx));
                cLayer5Data.setInt(ima.set(timeIdx), LayerC_5.get(timeIdx));

                CBNData.setInt(ima.set(timeIdx),ncb.get(timeIdx));
                Self_checkData.set(ima.set(timeIdx),sc.get(timeIdx));
                LBCHData.setInt(ima.set(timeIdx),hlbc.get(timeIdx));
                VVData.setInt(ima.set(timeIdx),hvv.get(timeIdx));
                SCBHData.setInt(ima.set(timeIdx),hslbc.get(timeIdx));
                HCBHData.setInt(ima.set(timeIdx),hhbc.get(timeIdx));
                AWSIData.set(ima.set(timeIdx),iaws.get(timeIdx));
                scaleData.setInt(ima1.set(timeIdx),scL.get(timeIdx));
                bcprofData.setInt(ima1.set(timeIdx),bclL.get(timeIdx));
                proflData.setInt(ima1.set(timeIdx),profL.get(timeIdx));
                laserpData.setInt(ima1.set(timeIdx),leL.get(timeIdx));
                lasertData.set(ima1.set(timeIdx),tempL.get(timeIdx));
                wtransData.setInt(ima1.set(timeIdx),wtL.get(timeIdx));
                tiltData.setInt(ima1.set(timeIdx),tiltL.get(timeIdx));
                bglightData.setInt(ima1.set(timeIdx),bclL.get(timeIdx));
                mparametersData.set(ima1.set(timeIdx),parL.get(timeIdx));
                SumData.setInt(ima1.set(timeIdx),sbacL.get(timeIdx));

               for(int j=0; j < 1539 ;j++) {
                   heightData.setInt(hi.set(j), h.get(j));
                    TWAB.set(ima2.set(timeIdx,j),hx2D.get(timeIdx).get(j));}
            }
            // write the data into netCDF file
                ncfile.write(time, timeData);
                ncfile.write(height, heightData);
                ncfile.write(cloudL1, cloudL1Data);
                ncfile.write(cLayer1, cLayer1Data);
                ncfile.write(cloudL2, cloudL2Data);
                ncfile.write(cLayer2, cLayer2Data);
                ncfile.write(cloudL3, cloudL3Data);
                ncfile.write(cLayer3, cLayer3Data);
                ncfile.write(cloudL4, cloudL4Data);
                ncfile.write(cLayer4, cLayer4Data);
                ncfile.write(cloudL5, cloudL5Data);
                ncfile.write(cLayer5, cLayer5Data);
                ncfile.write(CBN,CBNData);
                ncfile.write(Self_check,Self_checkData);
                ncfile.write(LBCH,LBCHData);
                ncfile.write(VV,VVData);
                ncfile.write(SCBH,SCBHData);
                ncfile.write(HCBH,HCBHData);
                ncfile.write(AWSI,AWSIData);
                ncfile.write(Scale,scaleData);
                ncfile.write(BPR,bcprofData);
                ncfile.write(LP,proflData);
                ncfile.write(LPE,laserpData);
                ncfile.write(LT,lasertData);
                ncfile.write(WTE,wtransData);
                ncfile.write(Tilt,tiltData);
                ncfile.write(BCL,bglightData);
                ncfile.write(MP,mparametersData);
                ncfile.write(SDNB,SumData);
                ncfile.write(TWABP,TWAB);

        } catch(IOException | InvalidRangeException e){
            e.printStackTrace();

        }
        finally{
            try{
                if(br != null)
                    br.close();
                if(fr != null)
                    fr.close();
                ncfile.close();

            }catch(IOException ex){
                ex.printStackTrace();

            }

        }
        
    }
    
    public static Date dateConv1(String s){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateInString = s;
        Date date = new Date();
        try {

             date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         return date;
    }

    /**
     * Converts a java.util.Date into a MATLAB Serial Date Number in the local timezone. MATLAB Serial Date Number are
     * doubles counting the number of days since January 0 0000. The time of day is represented in fractions of a whole
     * day.
     *
     * @param date the date to convert
     * @return the date as MATLAB Serial Date Number
     */
    public static double ToMATLABDate(Date date) {
        // Converts a java.util.Date into a MATLAB Serial Date Number taking into account timezones and DST.
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        double SerialDateNumber = (date.getTime() + cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)) / 1000.0 / 3600.0 / 24.0 ;
        return SerialDateNumber;
    }

    // Calculate the number of line in the readed file. The first two line is the header
    public static int countLines(String FILENAME) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(FILENAME));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;

        } finally {
            is.close();
            
        }
    }


    //Convert the two-way attenuated backscatter from 20 bit hexa to decimal

    public static ArrayList<Double> backscatter(String s){
        int digit1 = 0;
        double digit = 0.0;
        String ss="";
        double digit2=0.0;
        ArrayList<Double> bcdigit = new ArrayList<Double>();
        for(int i=0; i < s.length()-5; i=i+5){
        
            ss = s.substring(i,i+5);      //s is 5th line 770 character without end of line char.
            digit1 = Integer.parseInt(ss,16);  //convert hexadecimal to decimal
            digit = digit1 * 1.0;
               if(digit > Math.pow(2.0, 19.0)){
                 digit = -(Math.pow(2.0, 20.0) - digit);
                }

            digit2 = digit * 0.00001;   // convert 100000kmsr-1 to kmsr-1
            bcdigit.add(digit2);      
            
        }
        return bcdigit;
    }
    
}


        
    
    

