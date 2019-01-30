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
package io.repseq.gen.dist;

import io.repseq.gen.VDJCGenes;
import io.repseq.gen.VDJTrimming;

public interface VDJTrimmingGenerator {
    /**
     * Generate trimmings, given v, d, j, c genes.
     *
     * @param genes v, d, j, c genes
     * @return trimmings
     */
    VDJTrimming sample(VDJCGenes genes);
}
