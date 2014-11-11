package com.numergy.blog.csp.util;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.numergy.blog.csp.planning.entity.VirtualMachine;
import com.numergy.blog.csp.planning.fact.ComputeServer;


public class Utils {
	public static Collection<VirtualMachine> findVmsOnServer(final ComputeServer server, final List<VirtualMachine> virtualMachines) {
		return  Collections2.filter(virtualMachines, new Predicate<VirtualMachine>() {
			@Override
			public boolean apply(VirtualMachine input) {
				return input.getServer().equals(server);
			}
		});
	}
	
	public static int sumVirtualMachinesCpu(Collection<VirtualMachine> virtualMachines) {
		int sum = 0;
		for(VirtualMachine vm : virtualMachines) {
			sum += vm.getVcpu();
		}
		return sum;
	}
	
	public static int sumVirtualMachinesRam(Collection<VirtualMachine> virtualMachines) {
		int sum = 0;
		for(VirtualMachine vm : virtualMachines) {
			sum += vm.getVram();
		}
		return sum;
	}
}
