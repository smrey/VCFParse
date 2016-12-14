import java.io.*;
import java.util.LinkedHashMap;

/**
 * Created by Sara on 07-Nov-16.
 */

public class Main {

    public static void main(String[] args) {

        //Declare variables
        String path;


        //System.out.println("Hello World");

        //An instance of the class needs to be created before the non-static methods can be referenced
        //Instance of the TestClass, which I have called obj
        //TestClass obj = new TestClass ();

        //calling the parSer method of the TestClass within the obj instance of the class
        //obj.parSer();

        //Stuff for fiddling- move in a bit- to create the File object for the vcf
        //path="C:\\Users\\Admin\\Documents\\Work\\VCFtoTab\\ExampleFiles\\vcf.vcf";
        path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\vcf.vcf"; //Single sample vcf
        //path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_ann.vcf";
        path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_ann2.vcf"; //Bugged Ensembl transcripts
        //path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_ann_new.vcf";
        path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_ann_new2.vcf";
        path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\minimisation.vcf";
        path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multialleles.vcf";
        path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_annotated_minimisation.vcf";
        path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_annotated_multialleles.vcf";
        //path="C:\\Users\\Admin\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_annotated_multialleles.vcf";
        //path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_annotated_multialleles_test.vcf";
        //path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_gatk3-6.vcf";
        //path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_gatk3-6_test.vcf";
        //path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_gatk3-6_altalleles.vcf";
        //path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\multisample_gatk3-6_altalleles_test.vcf";
        //path="C:\\Users\\Sara\\Documents\\Work\\VCFtoTab\\ExampleFiles\\vcf.vcf";

        //Creates a new File instance by converting given pathname string into an abstract pathname
        File vcf_file = new File(path);

        //Verifying type is correct and the absolute filepath is correct
        //String type = ((Object)vcf_file).getClass().getName();
        //System.out.println(type);
        //System.out.println(vcf_file);

        //Instantiate second class
        VepVcf retrieveVepVcfData = new VepVcf(); //When return to data retrieval//!!
        ////t obj2 = new t(vcf_file); //temp for testing purposes

        //Open the file
        retrieveVepVcfData.parseVepVcf(retrieveVepVcfData.openFiles(vcf_file)); //When return to data retrieval//!!
        //System.out.println(obj3);
        //System.out.println(retrieveVepVcfData.getSampleVariantHashMap());
        //System.out.println(retrieveVepVcfData.getVariantHashMap());

        LinkedHashMap<String, SampleVariantDataObject> sampleVariantHashMap =
                retrieveVepVcfData.getSampleVariantHashMap();
        LinkedHashMap<String, VariantDataObject> variantHashMap = retrieveVepVcfData.getVariantHashMap();


        //TESTING READOUT
        ReadOut retrieveData = new ReadOut(variantHashMap, sampleVariantHashMap);
        retrieveData.readOutTest();



        /*
        for (String sampleVariantHashMapKey : sampleVariantHashMap.keySet()) {
            //System.out.println(test);
            //System.out.println(sampleVariantHashMap.get(test));
            String[] splitted = sampleVariantHashMapKey.split(",");
            //System.out.println(splitted[0]);
            //System.out.println(splitted[1]);
            //System.out.println(splitted[2]);
            //System.out.println(splitted[1]+splitted[2]);
            String forVariantRetrieval = splitted[1] + splitted[2];
            //tested below as working- retrieves the same variant data object for the same
            ////System.out.println(forVariantRetrieval);
            //This is null where the allele is the same as the reference for that sample- may want to remove later
            ////System.out.println(variantHashMap.get(forVariantRetrieval));

            //System.out.println(sampleVariantHashMap.get(forVariantRetrieval));

            //System.out.println(sampleVariantHashMapKey);
            //System.out.println(sampleVariantHashMap.get(sampleVariantHashMapKey).getAlleleDepth());
            //System.out.println(sampleVariantHashMap.get(sampleVariantHashMapKey).getSampleDataObject().getGenotypeQuality());

            //Searching for errored samples
            if ((sampleVariantHashMap.get(sampleVariantHashMapKey).getAlleleDepth() == 0) &&
                    (sampleVariantHashMap.get(sampleVariantHashMapKey).getSampleDataObject().getGenotypeQuality() != 0)){

                System.out.println(sampleVariantHashMapKey);
                System.out.println(sampleVariantHashMap.get(sampleVariantHashMapKey).getAlleleDepth());
                System.out.println(sampleVariantHashMap.get(sampleVariantHashMapKey).getSampleDataObject().getGenotypeQuality());
                System.out.println(sampleVariantHashMap.get(sampleVariantHashMapKey).getSampleDataObject().getZygosity());
            }
            */

        }
        //System.out.println(obj3.getCsqObject());

        //For multisample vcf
        //TestMultisampleVcf obj4 = new TestMultisampleVcf();
        //obj4.openMultisampleVcf(vcf_file);


    //Method probably not needed- delete later
    public static File getAbsoluteFile(File root, String path)  {
        File file = new File(path);
        if (file.isAbsolute())
                return file;
        if (root == null)
                return null;
        return new File(root, path);
    }
}
