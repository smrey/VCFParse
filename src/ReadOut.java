import java.util.LinkedHashMap;

/**
 * Created by Sara on 08-Dec-16.
 */
public class ReadOut {

    private LinkedHashMap<String, VariantDataObject> variants;
    private LinkedHashMap<String, SampleVariantDataObject> sampleVariants;


    public ReadOut(LinkedHashMap<String, VariantDataObject> variants,
                   LinkedHashMap<String, SampleVariantDataObject> sampleVariants){
        this.variants = variants;
        this.sampleVariants = sampleVariants;
    }


    public void readOutTest(){

        for (String sampleVariantHashMapKey : sampleVariants.keySet()) {

            //Skip hom ref sites, which we don't want to readout- or should we vaoid storing them


            //System.out.println(test);
            //System.out.println(sampleVariantHashMap.get(test));
            String[] splitKey = sampleVariantHashMapKey.split(",");
            //System.out.println(splitKey[0]);
            //System.out.println(splitKey[1]);
            //System.out.println(splitKey[2]);
            //System.out.println(splitKey[1]+splitKey[2]);
            String forVariantRetrieval = splitKey[1] + splitKey[2];
            //tested below as working- retrieves the same variant data object for the same
            ////System.out.println(forVariantRetrieval);
            //This is null where the allele is the same as the reference for that sample- may want to remove later
            ////System.out.println(variantHashMap.get(forVariantRetrieval));

            //System.out.println(sampleVariantHashMap.get(forVariantRetrieval));

            //System.out.println(sampleVariantHashMapKey);
            //System.out.println(sampleVariants.get(sampleVariantHashMapKey).getSampleDataObject().getSampleName());



            //Commented out to work on CSQ identification, duplication issue and logic changes to link ONLY the
               //relevant VepAnnotationObjects to each allele. At present all (for each variant context) are linked
               //to every allele in that variant context.


            System.out.println(forVariantRetrieval);
            System.out.println(sampleVariants.get(sampleVariantHashMapKey).getVariantObjectKey()); //Check data correct
            //Allele frequency
            //System.out.println(sampleVariants.get(sampleVariantHashMapKey).getSampleDataObject().getGenotypeQuality());
            //System.out.println(variants);
            System.out.println(variants.get(forVariantRetrieval).getIdField()); //dbSNP
            System.out.println(sampleVariants.get(sampleVariantHashMapKey).getAlleleNum()); //null pointer exception for ref allele-FIX

            //Allele frequency
            System.out.println(sampleVariants.get(sampleVariantHashMapKey).getAlleleFrequency());

            //Get required data from CSQ fields

            for (VepAnnotationObject vepAnnObj : variants.get(forVariantRetrieval).getCsqObject()) {
                System.out.println(vepAnnObj.getVepRecord().get("AFR_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("AMR_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("EAS_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("EUR_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("SAS_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("ExAC_AFR_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("ExAC_AMR_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("ExAC_EAS_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("ExAC_FIN_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("ExAC_NFE_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("ExAC_OTH_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("ExAC_SAS_MAF"));
                System.out.println(vepAnnObj.getVepRecord().get("Transcript"));
                System.out.println(vepAnnObj.getVepRecord().get("PreferredTranscript"));
                System.out.println(vepAnnObj.getVepRecord().get("Gene"));
                System.out.println(vepAnnObj.getVepRecord().get("HGVSc"));
                System.out.println(vepAnnObj.getVepRecord().get("HGVSp"));
                System.out.println(vepAnnObj.getVepRecord().get("Consequence"));
                System.out.println(vepAnnObj.getVepRecord().get("Exon/Intron"));
                System.out.println(vepAnnObj.getVepRecord().get(""));
                System.out.println(vepAnnObj.getVepRecord().get(""));
                System.out.println(vepAnnObj.getVepRecord().get(""));
                System.out.println(vepAnnObj.getVepRecord().get(""));
            }


            System.out.println(variants.get(forVariantRetrieval).getCsqObject()); //Multiple VepAnnotation objects for this variant


            for (VepAnnotationObject vepAnnObj : variants.get(forVariantRetrieval).getCsqObject()) {
                System.out.println(vepAnnObj.getEntireVepRecordValues());
            }

            //System.out.println(variants.get(forVariantRetrieval).getCsqObject().getClass());


            //System.out.println(variants.get(forVariantRetrieval).getCsqObject().getEntireCsqObject());
            //System.out.println(variants.get(forVariantRetrieval).getCsqObject().getSpecificCsqObject(1));
            //System.out.println(variants.get(forVariantRetrieval).getCsqObject().getCsqObjectVepAnnotationValues(1));





            //Calculate allele frequency

        }

    }


}
