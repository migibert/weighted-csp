package com.numergy.blog.csp.planning.fact;

public class ComputeServer {
	private String name;
	private int ram;
	private int cpu;
	private int cost;
	private int reliability;

	public ComputeServer(String name, int ram, int cpu, int cost, int reliability) {
		this.name = name;
		this.ram = ram;
		this.cpu = cpu;
		this.cost = cost;
		this.reliability = reliability;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getReliability() {
		return reliability;
	}
	
	public void setReliability(int reliability) {
		this.reliability = reliability;
	}
}
