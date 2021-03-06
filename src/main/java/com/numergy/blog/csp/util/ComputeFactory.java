package com.numergy.blog.csp.util;

import java.util.ArrayList;
import java.util.List;

import com.numergy.blog.csp.planning.entity.VirtualMachine;
import com.numergy.blog.csp.planning.fact.ComputeServer;

public class ComputeFactory {
	public static List<VirtualMachine> buildVirtualMachines(int[] vram, int[] vcpu) {
		if(vram.length != vcpu.length) {
			throw new IllegalArgumentException("vram array and vcpu array should have the same length");
		}
		
		List<VirtualMachine> vms = new ArrayList<>(vram.length);
		for(int i=0; i<vram.length; i++) {
			vms.add(new VirtualMachine("VM " + i, vram[i], vcpu[i]));
		}
		
		return vms;
	}
	
	public static List<ComputeServer> buildComputeServer(int[] ram, int[] cpu, int[] cost, int[] reliability) {
		if(ram.length != cpu.length || ram.length != cost.length || cpu.length != cost.length) {
			throw new IllegalArgumentException("ram array, cost array and cpu array should have the same length");
		}
		
		List<ComputeServer> servers = new ArrayList<>(ram.length);
		for(int i=0; i<ram.length; i++) {
			servers.add(new ComputeServer("Server " + i, ram[i], cpu[i], cost[i], reliability[i]));
		}
		
		return servers;
	}
}
