package org.yuxin.ecloud.bloom.filter.local.test;

import org.yuxin.ecloud.bloom.filter.core.BloomFilter;
import org.yuxin.ecloud.bloom.filter.local.LocalBloomFilterManager;
import org.yuxin.ecloud.core.constants.ECloudConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = LocalBloomFilterTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication(scanBasePackages = ECloudConstants.BASE_COMPONENT_SCAN_PATH + ".bloom.filter.local")
@Slf4j
public class LocalBloomFilterTest {

    @Autowired
    private LocalBloomFilterManager manager;

    /**
     * 测试本地布隆过滤器
     */
    @Test
    public void localBloomFilterTest() {
        BloomFilter<Integer> bloomFilter = manager.getFilter("test");
        long failNum = 0L;
        for (int i = 0; i < 1000000; i++) {
            bloomFilter.put(i);
        }
        for (int j = 1000000; j < 1100000; j++) {
            boolean result = bloomFilter.mightContain(j);
            if (result) {
                failNum++;
            }
        }
        log.info("test num {}, fail num {}", 100000, failNum);
    }

}
