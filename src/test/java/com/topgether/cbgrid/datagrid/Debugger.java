/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package com.topgether.cbgrid.datagrid;

import org.gridgain.grid.Grid;
import org.gridgain.grid.GridException;
import org.gridgain.grid.GridGain;

import com.topgether.cbgrid.task.SampleComputeNumTask;

/**
 * Demonstrates how to explicitly deploy a task. Note that
 * it is very rare when you would need such functionality as tasks are
 * auto-deployed on demand first time you execute them. So in most cases
 * you would just apply any of the {@code Grid.execute(...)} methods directly.
 * However, sometimes a task is not in local class path, so you may not even
 * know the code it will execute, but you still need to execute it. For example,
 * you have two independent components in the system, and one loads the task
 * classes from some external source and deploys it; then another component
 * can execute it just knowing the name of the task.
 * <p>
 * Also note that for simplicity of the example, the task we execute is
 * in system classpath, so even in this case the deployment step is unnecessary.
 * <p>
 * Remote nodes should always be started with special configuration file which
 * enables P2P class loading: {@code 'ggstart.{sh|bat} examples/config/example-cache.xml'}.
 * <p>
 * Alternatively you can run {@link ComputeNodeStartup} in another JVM which will
 * start GridGain node with {@code examples/config/example-compute.xml} configuration.
 */
public final class Debugger {
    /** Name of the deployed task. */
	
    public static void main(String[] args) throws GridException {
      try {
    	  Grid g = GridGain.start();
          System.out.println(">>> Deployment example started.");
          
//          g.compute().localDeployTask(SampleComputeNumTask.class, SampleComputeNumTask.class.getClassLoader());
//          for (Map.Entry<String, Class<? extends GridComputeTask<?, ?>>> e : g.compute().localTasks().entrySet())
//              System.out.println(">>> Found locally deployed task [alias=" + e.getKey() + ", taskCls=" + e.getValue());
          
//          g.compute().execute(SampleComputeNumTask.class,null).get();
//          g.compute().undeployTask(TASK_NAME);

          System.out.println();
          System.out.println(">>> Finished executing Grid Direct Deployment Example.");
          System.out.println(">>> Check participating nodes output.");
      } catch (Exception e) {
    	  e.printStackTrace();
      }
  }
    
}
