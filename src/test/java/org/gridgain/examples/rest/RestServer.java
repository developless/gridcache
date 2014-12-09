package org.gridgain.examples.rest;

import static org.gridgain.grid.cache.GridCacheAtomicityMode.TRANSACTIONAL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.gridgain.client.GridClientFactory;
import org.gridgain.grid.Grid;
import org.gridgain.grid.GridConfiguration;
import org.gridgain.grid.GridDeploymentMode;
import org.gridgain.grid.GridException;
import org.gridgain.grid.cache.GridCacheConfiguration;
import org.gridgain.grid.cache.GridCacheMode;
import org.gridgain.grid.spi.discovery.tcp.GridTcpDiscoverySpi;
import org.gridgain.grid.spi.discovery.tcp.ipfinder.multicast.GridTcpDiscoveryMulticastIpFinder;
import org.gridgain.grid.spi.discovery.tcp.ipfinder.vm.GridTcpDiscoveryVmIpFinder;
import org.gridgain.grid.util.GridConcurrentFactory;
import org.gridgain.grid.util.typedef.G;

public class RestServer {
	
//	static{
//		System.setProperty("GRIDGAIN_JETTY_HOST", "127.0.0.1");
//		System.setProperty("GRIDGAIN_JETTY_PORT", "8090");
//	}
	
	public static void main(String[] args) throws Exception {
		Grid grid = G.start(configure());
//		grid.compute().localDeployTask(SampleComputeNumTask.class, SampleComputeNumTask.class.getClassLoader());
//		grid.compute().localDeployTask(CharacterCountTask.class, CharacterCountTask.class.getClassLoader());
//		grid.compute().execute(CharacterCountTask.class, "你 好 吗 我 很 好 啊").get();
//		String url = "http://localhost:8080/gridgain?cmd=exe&async=true&name=CharacterCountTask&clientId=e67fce2c-8b22-4965-a87d-b91c3b6c65f6&p1=heollowwoaa";
//		String url = "http://localhost:8090/gridgain?cmd=exe&async=true&name=CharacterCountTask&clientId=e67fce2c-8b22-4965-a87d-b91c3b6c65f6&p1=heollowwoaa";
//		String str = httpGet(url);
		
//		System.out.println(str);
	}
	
	private static String httpGet(String url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        int responseCode = conn.getResponseCode();
        if (200 != responseCode) {
            throw new RuntimeException("unexpected response code " + responseCode + " from " + url);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
        try {
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } finally {
            reader.close();
        }
    }

	 /**
     * Configure grid.
     *
     * @return Grid configuration.
     * @throws GridException If failed.
     */
    public static GridConfiguration configure() throws GridException {
    	
    	GridConfiguration cfg = new GridConfiguration();

        cfg.setLocalHost("localhost");

        // Discovery SPI.
        GridTcpDiscoverySpi discoSpi = new GridTcpDiscoverySpi();

        GridTcpDiscoveryVmIpFinder ipFinder = new GridTcpDiscoveryMulticastIpFinder();

        ipFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47509"));

        discoSpi.setIpFinder(ipFinder);

        GridCacheConfiguration cacheCfg = new GridCacheConfiguration();
        // Set atomicity as transaction, since we are showing transactions in example.
        cacheCfg.setName("share-hqfx");
        cacheCfg.setAtomicityMode(TRANSACTIONAL);
        cacheCfg.setCacheMode(GridCacheMode.REPLICATED);
        cacheCfg.setQueryIndexEnabled(false);
        cfg.setDiscoverySpi(discoSpi);
        cfg.setCacheConfiguration(cacheCfg);
        cfg.setDeploymentMode(GridDeploymentMode.CONTINUOUS);

        return cfg;
    }
    
}
