package com.topgether.cbgrid.task;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.gridgain.grid.compute.GridComputeJob;
import org.gridgain.grid.compute.GridComputeJobAdapter;
import org.gridgain.grid.compute.GridComputeJobResult;
import org.gridgain.grid.compute.GridComputeTaskName;
import org.gridgain.grid.compute.GridComputeTaskSplitAdapter;
import org.jetbrains.annotations.Nullable;

@GridComputeTaskName("CharacterCountTask")
public class CharacterCountTask extends GridComputeTaskSplitAdapter<String, Integer> {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Splits the received string to words, creates a child job for each word, and sends
     * these jobs to other nodes for processing. Each such job simply prints out the received word.
     *
     * @param gridSize Number of available grid nodes. Note that returned number of
     *      jobs can be less, equal or greater than this grid size.
     * @param arg Task execution argument. Can be {@code null}.
     * @return The list of child jobs.
     */
    @Override protected Collection<? extends GridComputeJob> split(int gridSize, String arg) {
        Collection<GridComputeJob> jobs = new LinkedList<GridComputeJob>();

        for (final String word : arg.split("o")) {
            jobs.add(new GridComputeJobAdapter() {

            	private static final long serialVersionUID = 1L;

				@Nullable @Override public Object execute() {
                    System.out.println();
                    System.out.println(">>> Printing '" + word + "' on this node from grid job.");

                    // Return number of letters in the word.
                    return word.length();
                }
            });
        }

        return jobs;
    }

    /** {@inheritDoc} */
    @Nullable @Override public Integer reduce(List<GridComputeJobResult> results) {
        int sum = 0;

        for (GridComputeJobResult res : results)
            sum += res.<Integer>getData();

        return sum;
    }
}

