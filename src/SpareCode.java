import htsjdk.tribble.AbstractFeatureReader;
import htsjdk.tribble.readers.LineIterator;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.variantcontext.writer.Options;
import htsjdk.variant.variantcontext.writer.VariantContextWriter;
import htsjdk.variant.variantcontext.writer.VariantContextWriterBuilder;
import htsjdk.variant.vcf.VCFCodec;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sara on 15-Nov-16.
 */
public class SpareCode {

    public SpareCode(File vcfFilePath) {
        this.vcfFilePath = vcfFilePath;
    }

    private static final Logger Log = Logger.getLogger(OpenVEPVCF.class.getName());
    private File vcfFilePath;

    public void openWriteFile() {
        //Need these temporarily for the code below to execute- find a better solution later
        File inputFile = vcfFilePath;
        File outputFile = null;

        try (final VariantContextWriter writer = outputFile == null ? null : new VariantContextWriterBuilder().setOutputFile(outputFile).setOutputFileType(VariantContextWriterBuilder.OutputType.VCF).unsetOption(Options.INDEX_ON_THE_FLY).build();
             final AbstractFeatureReader<VariantContext, LineIterator> reader = AbstractFeatureReader.getFeatureReader(inputFile.getAbsolutePath(), new VCFCodec(), false)) {
        } catch (Exception e) {

            Log.log(Level.SEVERE, "Could not read VCF file: " + e.getMessage());
        }
    }
}