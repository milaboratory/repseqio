/*
 * Copyright 2019 MiLaboratory, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.repseq.cli;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExportCloneSequencesActionTest {
    @Test
    public void randomRoundTest() throws Exception {
        double value = 12.34;
        double sum = 0.0;
        RandomGenerator random = new Well19937c(1232434);
        for (int i = 0; i < 100000; i++)
            sum += ExportCloneSequencesAction.randomizedRound(value, random);
        assertEquals(value, sum / 100000, 0.1);
    }
}