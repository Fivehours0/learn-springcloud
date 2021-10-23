package com.springcloud.lb.impl;

import com.springcloud.lb.LoadBalance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLb implements LoadBalance {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int increaceAndGet() {
        for(;;) {
            int curr = atomicInteger.get();
            int next = curr >= Integer.MAX_VALUE ? 0 : curr + 1;
            if (atomicInteger.compareAndSet(curr, next)) {
                return next;
            }
        }
    }

    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {
        int index = increaceAndGet() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
