package io.repseq.learn;

import com.milaboratory.core.sequence.NucleotideSequence;
import io.repseq.learn.param.SimpleInsertionParameters;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mikesh on 7/11/17.
 */
public class VJTransitionGeneratorTest {
    private final VJTransitionGenerator transitionGenerator = new VJTransitionGenerator(
            DummyVJParameters.vjInsertionParameters,
            DummyVJParameters.germlineMatchParameters,
            DummyVJParameters.germlineSequenceProvider,
            DummyVJParameters.segmentTrimmingProvider
    );

    private final SegmentTuple fixedTrimSegments = new SegmentTuple("V0", "J0"),
            flexTrimSegments = new SegmentTuple("V0", "J1"),
            flexTrimSegments2 = new SegmentTuple("V0", "J2");

    @Test
    public void testFixed() {
        VJHmmTransitions vjHmmTransitions = transitionGenerator.generate(fixedTrimSegments,
                new NucleotideSequence("ATCAGCCATGCA" + "ACGCGTGC"));

        VDJPartitioning vdjPartitioning = vjHmmTransitions.computeBestPartitioning();

        Assert.assertEquals(12, vdjPartitioning.getvEnd());
        Assert.assertEquals(12, vdjPartitioning.getjStart());
        Assert.assertEquals(DummyVJParameters.vjInsertionParameters.getInsertSizeProb(0),
                vjHmmTransitions.computePartialProbability(), 1e-10);
    }

    @Test
    public void testInsert() {
        VJHmmTransitions vjHmmTransitions = transitionGenerator.generate(fixedTrimSegments,
                new NucleotideSequence("ATCAGCCATGCA" + "A" + "ACGCGTGC"));

        VDJPartitioning vdjPartitioning = vjHmmTransitions.computeBestPartitioning();

        Assert.assertEquals(12, vdjPartitioning.getvEnd());
        Assert.assertEquals(13, vdjPartitioning.getjStart());
        Assert.assertEquals(DummyVJParameters.vjInsertionParameters.getInsertSizeProb(1) *
                        SimpleInsertionParameters.PROB,
                vjHmmTransitions.computePartialProbability(), 1e-10);

        vjHmmTransitions = transitionGenerator.generate(flexTrimSegments,
                new NucleotideSequence("ATCAGCCATGCA" + "ATGAG" + "GCGTGC"));

        vdjPartitioning = vjHmmTransitions.computeBestPartitioning();

        Assert.assertEquals(12, vdjPartitioning.getvEnd());
        Assert.assertEquals(17, vdjPartitioning.getjStart());
        Assert.assertEquals(DummyVJParameters.vjInsertionParameters.getInsertSizeProb(5) *
                        Math.pow(SimpleInsertionParameters.PROB, 5),
                vjHmmTransitions.computePartialProbability(), 2e-5);
    }

    @Test
    public void testDeletions() {
        VJHmmTransitions vjHmmTransitions = transitionGenerator.generate(fixedTrimSegments,
                new NucleotideSequence("ATCAGCCATGCA" + "CGCGTGC"));

        Assert.assertEquals(0.0, vjHmmTransitions.computePartialProbability(), 1e-10);
        // bounds undefined

        vjHmmTransitions = transitionGenerator.generate(flexTrimSegments,
                new NucleotideSequence("ATCAGCCATGCA" + "CGCGTGC"));
        VDJPartitioning vdjPartitioning = vjHmmTransitions.computeBestPartitioning1();

        Assert.assertTrue(vjHmmTransitions.computePartialProbability() > 0);
        Assert.assertEquals(12, vdjPartitioning.getvEnd());
        Assert.assertEquals(13, vdjPartitioning.getjStart());

        vjHmmTransitions = transitionGenerator.generate(flexTrimSegments2,
                new NucleotideSequence("ATCAGCCATGCA" + "CGGCGAATTATGC"));
        vdjPartitioning = vjHmmTransitions.computeBestPartitioning1();

        Assert.assertTrue(vjHmmTransitions.computePartialProbability() > 0);
        Assert.assertEquals(12, vdjPartitioning.getvEnd());
        Assert.assertEquals(13, vdjPartitioning.getjStart());

        vjHmmTransitions = transitionGenerator.generate(flexTrimSegments2,
                new NucleotideSequence("ATCAGCCATGCA" + "GGCGAATTATGC"));
        vdjPartitioning = vjHmmTransitions.computeBestPartitioning1();

        Assert.assertTrue(vjHmmTransitions.computePartialProbability() > 0);
        Assert.assertEquals(12, vdjPartitioning.getvEnd());
        Assert.assertEquals(12, vdjPartitioning.getjStart());
    }
}