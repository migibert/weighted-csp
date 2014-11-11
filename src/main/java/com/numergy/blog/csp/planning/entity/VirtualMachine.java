package com.numergy.blog.csp.planning.entity;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import com.numergy.blog.csp.planning.fact.ComputeServer;

@PlanningEntity
public class VirtualMachine {
	private ComputeServer server;	
	private String name;
	private int vram;
	private int vcpu;

	public VirtualMachine() {		
	}
	
	public VirtualMachine(String name, int vram, int vcpu) {
		this.name = name;
		this.vram = vram;
		this.vcpu = vcpu;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVram() {
		return vram;
	}

	public void setVram(int vram) {
		this.vram = vram;
	}

	public int getVcpu() {
		return vcpu;
	}

	public void setVcpu(int vcpu) {
		this.vcpu = vcpu;
	}	
	
	@PlanningVariable(valueRangeProviderRefs = {"computeServersRangeProvider"})
	public ComputeServer getServer() {
		return server;
	}
	
	public void setServer(ComputeServer server) {
		this.server = server;
	}
}
