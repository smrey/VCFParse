//Dependencies htsjdk library 2.7.0
/*
Comment here
 */

import java.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import htsjdk.samtools.*;
import htsjdk.tribble.*;
import htsjdk.tribble.readers.*;
import htsjdk.variant.variantcontext.*;
import htsjdk.variant.variantcontext.writer.*;
import htsjdk.samtools.util.CloseableIterator;
import htsjdk.variant.vcf.*;
import java.util.LinkedHashMap;

import htsjdk.variant.utils.*;
import java.util.List;
import java.util.ArrayList;
import org.broadinstitute.hellbender.utils.reference.ReferenceUtils.*;
import com.google.common.collect.Multimap;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import java.util.Collection;


/**
 * Created by Sara on 08-Nov-16.
 */

public class VepVcf {

    private static final Logger Log = Logger.getLogger(OpenVEPVCF.class.getName());
    //private File vcfFilePath;

    //Maybe move this inside the method
    private LinkedHashMap<GenomeVariant, CsqObject> variantHashMap =  new LinkedHashMap<GenomeVariant, CsqObject>(); //Linked hash map preserves order

    //Constructor- invoked at the time of object creation
    //public VepVcf(File vcfFilePath) {
        //this.vcfFilePath = vcfFilePath;
    //}

    public VCFFileReader openFiles(File vcfFilePath) { //throws IOException  {
        Log.log(Level.INFO, "Opening VEP VCF file");
        //Read in the file
        //try(final VCFFileReader vcfFile = new VCFFileReader(vcfFilePath, false)){
        //VCFHeader currentHeader = vcfFile.getFileHeader();
        //System.out.println(currentHeader); //The file header

        try{
            final VCFFileReader vcfFile = new VCFFileReader(vcfFilePath, false);
            return vcfFile;
        }catch(Exception e) {
            Log.log(Level.SEVERE, "Could not read VCF file: " + e.getMessage());
        }
        return null; //This is what it returns if the try catch block fails- syntax??
    }

    public LinkedHashMap parseVepVcf(VCFFileReader vcfFile)

    {
        Log.log(Level.INFO, "Parsing VEP VCF file");
        //For the alternate alleles
        //Required for code execution as otherwise variable is initialised only in else clause
        String altAllele = null;
        for (final VariantContext vc : vcfFile){
            //System.out.println(vc.getContig());
            //System.out.print(vc.getContig());
            //System.out.print("\t");
            //System.out.print(vc.getStart());
            //System.out.print("\t");
            //System.out.print(vc.getEnd()); //Could be useful for indels etc.
            //System.out.print("\t");
            //System.out.print(vc.getAttributeAsList("ID")); //This is returning null at present- no key found?
            //System.out.print("\t");
            //Allele refAl = vc.getReference();
            //System.out.print(refAl); //Reference allele
            //System.out.print("\t");
            //System.out.println(vc.getAlleles()); //Returns all the alleles
            List<Allele> altAlleles = vc.getAlternateAlleles();
            //System.out.println(altAlleles); //Returns all the potential alternate alleles- test with an indel
            //System.out.print("\t");
            //System.out.print(vc.getID());
            //System.out.print("\t");
            //System.out.print(vc.getPhredScaledQual());
            //System.out.print("\t");
            //System.out.print(vc.isFiltered());
            //System.out.print("\t");
            //System.out.print(vc.getAttribute("DP")); //Depth
            //System.out.print("\t");
            //System.out.print(vc.getAttribute("CSQ")); //Long- commented out for now
            //System.out.print("\t");
            //Need to find a better way of getting a transcript
            ///System.out.print(VariantContextUtils.match(VariantContext vc, ));
            //System.out.print("\t");

            //System.out.print(vc.hasAttribute("Transcript")); //To see if a particular attribute is available as a key
            //System.out.print("\t");


            ///Requires further parsing- do later
            //System.out.print(vc.getGenotypes());
            //System.out.print("\t");


            ///System.out.print(vc.getAttributes()); //Allows to obtain what is in the INFO field

            if (altAlleles.size() > 1){
                //System.out.println("Loop");
                //altAllele = altAlleles.get(0).toString();
                //System.out.println(altAlleles.size());

                //Logic required here to deal with more than one alternate allele//
                //Or perhaps we directly put it out as a List/Array- deal with in getter for this//



                //Create an appropriate store to associate the specific csq entries with the alt allele
                Multimap<String,VepAnnotationObject> alleleCsq = ArrayListMultimap.create();
                //new Multimap<String,VepAnnotationObject>(); //Consider creation of a separate object?

                //Create genome variant object for each allele
                //Allele variable has to be an object for compatibility with else (method returns object)
                for (Allele Alleles: altAlleles) {

                    //System.out.println(Alleles.getBaseString());
                    altAllele = Alleles.getBaseString();
                    //System.out.println(altAllele); //This is the alternate allele

                    //Create the GenomeVariant object to act as a key for this allele
                    GenomeVariant variantObject = new GenomeVariant(vc.getContig(), vc.getStart(),
                            vc.getReference().toString().replaceAll("\\*", ""), altAllele);

                    System.out.println(variantObject);

                    //Obtain variant object for this allele
                    //How many are there?

                    //May be able to remove this second loop- COMMENTED OUT 23/11/2016 16:45
                    //for (int i = 0; i < altAlleles.size(); i++){
                        //System.out.println(i);
                        //The entire CSQ record including all of the entries for this variant context
                        CsqUtilities currentCsqRecord = new CsqUtilities();

                        //Retrieve Csqs
                        CsqObject currentCsqObject = new CsqObject(); //Empty object created
                        //currentCsqObject.setCsqObject((currentCsqRecord.csqRecord(currentCsqRecord.vepHeaders(vcfFile),
                        //currentCsqRecord.vepAnnotations(vc))));

                        //This code is needed to populate the CSQ object- this is incorrectly populated with the types
                        //not going in as the correct object type (for the annotations) which leads to an issue
                        //with retrieval later
                        //(where the retrieved items are generic objects instead of VepAnnotationObjects)
                        currentCsqObject.setCsqObject((currentCsqRecord.csqRecord(currentCsqRecord.vepHeaders(vcfFile),
                                currentCsqRecord.vepAnnotations(vc))));

                        //See what is in the current CSQ object- this contains all the csq entries for ALL alternate alleles
                        //System.out.println(currentCsqObject.getEntireCsqObject());
                        //System.out.println(currentCsqObject.getCsqObjectVepAnnotationValues(1));

                        //Change the below to return a VepAnnotationObject
                        //VepAnnotationObject vepA1 = currentCsqRecord.vepAnnotations(vc);

                        //System.out.println(currentCsqObject.getCsqObject());

                    //This for loop needs to start at 1 because of the current naming of the CSQ Objects numerically
                        for (int j = 1; j <= currentCsqObject.getCsqObject().size(); j++ ){

                            VepAnnotationObject vepAnn = currentCsqObject.getSpecificCsqObject(j); //This is the particular Vep record already

                            //System.out.println(vepAnn.getAlleleNum());
                            //System.out.println(altAllele);
                            //System.out.println(altAlleles);

                            int alleleIndex = (Integer.parseInt(vepAnn.getAlleleNum()) - 1); //Java is zero-indexed (-1)

                            //System.out.println(altAlleles.get(alleleIndex));
                            //System.out.println(altAlleles.get(alleleIndex).getClass());
                            //System.out.println(altAlleles.get(alleleIndex).getBaseString());

                            alleleCsq.put((altAlleles.get(alleleIndex).getBaseString()),(vepAnn));

                            //System.out.println(alleleCsq); //Could be a useful statement when deciding on which for loops to keep


                        }

                   // }
                    //This printout will have all the data when the final allele comes through the loop
                    //System.out.println(alleleCsq);

                }
                //Test Multimap contains all expected data- NOTE Multimap is PER variant entry at present
                //System.out.println(alleleCsq); // This dictionary contains all the data for all alleles

                //Retrieve all entries for each allele and generate a csq object
                for (String key : alleleCsq.keySet()){
                    //System.out.println(alleleCsq.get(key).getClass());
                    //System.out.println(alleleCsq.get(key));
                    //Collection<VepAnnotationObject> m = alleleCsq.get(key);
                    ArrayList forCsq = new ArrayList(alleleCsq.get(key)); //Get the correct type for this object
                    System.out.println(key + " " + alleleCsq.get(key));
                    //System.out.println(test.getClass());
                    //System.out.println(m);
                    //System.out.println(forCsq.get(0));

                    CsqUtilities currentCsqRecord = new CsqUtilities();
                    System.out.println(currentCsqRecord.createCsqRecord(forCsq)); //Creation of csqObject

                    //Need to create a new csqObject to put into the variant hashmap, as the previous one is associated
                    //with the entire CSQ for all alternate alleles

                    /*
                    CsqObject currentCsqObject = new CsqObject(); //Empty object created
                    currentCsqObject.setCsqObject((currentCsqRecord.csqRecord(currentCsqRecord.vepHeaders(vcfFile),
                            currentCsqRecord.vepAnnotations(vc))));

                    for (VepAnnotationObject vepEntry : alleleCsq.get(key)){ //Iterate through Collection<VepAnnotationObject>
                        System.out.println(vepEntry);
                    }
                    */

                    //Turn the multiple VepAnnotationObject entries into a CsqObject


                }


            }else {
                //System.out.println("No loop");
                altAllele = altAlleles.get(0).getBaseString(); //Requires String for GenomeVariant class
                //System.out.println(altAllele);

                //This is intended as the key to the hashmap
                GenomeVariant variantObject = new GenomeVariant(vc.getContig(), vc.getStart(),
                        vc.getReference().toString().replaceAll("\\*", ""), altAllele);

                //Just some code to show that the convert to minimal representation method of GenomeVariant works
            /*
            System.out.println(variantObject);
            System.out.println(variantObject.getRef());
            System.out.println(variantObject.getAlt());
            variantObject.convertToMinimalRepresentation();
            System.out.println(variantObject.getRef());
            System.out.println(variantObject.getAlt());
            */

                //System.out.print(variantObject); //This can be the key for each variant entry
                //System.out.print("\n");

                //Make the object to hold the annotations- note this currently iterates every time and gets the same headers (same vcf)
                //Obtain keys for each transcript entry (header in vcf file)

                //The entire CSQ record including all of the entries for this variant context
                CsqUtilities currentCsqRecord = new CsqUtilities();
                //System.out.println(csqObject); //Just gives a reference to the object

                ////SOME LOGIC HERE- FOR MULTIALLELE
                //System.out.println(currentCsqRecord.parseCsq(vc));

                //c.vepHeaders(); //This object should contain the headers
                //System.out.println(currentCsqRecord.vepHeaders(vcfFile)); //Checking that the object returns the headers

                //c.vepAnnotations(vc); //This object should be an ArrayList of the annotations in the CSQ field
                //System.out.println(currentCsqRecord.vepAnnotations(vc)); //Checking that the object returns the datalist

                //Create a CSQ recordset per this Variant Context entry
                //c.CSQRecord(c.vepHeaders(vcfFile),c.vepAnnotations(vc)); //Might be worth retrieving the headers outside of this loop
                //System.out.println(c.CSQRecord(c.vepHeaders(vcfFile),c.vepAnnotations(vc))); //Print to check

                //Create a CsqObject to hold the data paired with the Genome Variant object as the key
                CsqObject currentCsqObject = new CsqObject(); //Empty object created
                currentCsqObject.setCsqObject((currentCsqRecord.csqRecord(currentCsqRecord.vepHeaders(vcfFile),
                        currentCsqRecord.vepAnnotations(vc))));
                //Might be worth retrieving the headers outside of this loop

                //Associate the variant object with the CsqObject on a per record basis
                variantHashMap.put(variantObject, currentCsqObject);


                //csqObject.vepHashMap(csqObject.vepHeaders(vcfFile),csqObject.vepAnnotations(vc)); //FIX THIS LINE

                //CSQObject t = new CSQObject(c.vepHeaders(), c.vepAnnotations(vc));
                //csqObject.vepAnnotations(vc).toString().replaceAll("^\\[","").replaceAll("\\]$",""));
                //t.tester();

                //Testing to determine what is inside each CsqObject- working on access
                //System.out.println(currentCsqObject.getCsqObject());
                //System.out.println(currentCsqObject.getEntireCsqObject());
                //System.out.println(currentCsqObject.getCsqObjectVepAnnotationValues(1));
                //System.out.println(currentCsqObject.getCsqObject2(1));
                //System.out.println(currentCsqObject.getCsqObject3(1));
            }
        }

        //Test hash map is working correctly
        //System.out.println(variantHashMap);
        //System.out.print("\n");
        return variantHashMap;

    }
}
