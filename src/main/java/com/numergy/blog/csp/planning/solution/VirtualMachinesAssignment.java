package com.numergy.blog.csp.planning.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import com.numergy.blog.csp.planning.entity.VirtualMachine;
import com.numergy.blog.csp.planning.fact.ComputeServer;
import com.numergy.blog.csp.util.Utils;

@PlanningSolution
public class VirtualMachinesAssignment implements Solution<HardSoftScore> {
	private List<ComputeServer> computeServers;
	private List<VirtualMachine> virtualMachine;

	private HardSoftScore score;

	@ValueRangeProvider(id = "computeServersRangeProvider")
	public List<ComputeServer> getComputeServersList() {
		return computeServers;
	}

	public void setComputeServersList(List<ComputeServer> computeServers) {
		this.computeServers = computeServers;
	}

	@PlanningEntityCollectionProperty
	public List<VirtualMachine> getVirtualMachinesList() {
		return virtualMachine;
	}

	public void setVirtualMachinesList(List<VirtualMachine> virtualMachine) {
		this.virtualMachine = virtualMachine;
	}

	public HardSoftScore getScore() {
		return score;
	}

	public void setScore(HardSoftScore score) {
		this.score = score;
	}

	@Override
	public Collection<? extends Object> getProblemFacts() {
		List<ComputeServer> facts = new ArrayList<>();
		facts.addAll(computeServers);
		return facts;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (ComputeServer server : computeServers) {
			builder.append("Server : ").append(server.getName()).append("\n");

			Collection<VirtualMachine> vmsOnServer = Utils.findVmsOnServer(server, virtualMachine);
			for (VirtualMachine vm : vmsOnServer) {
				builder.append("VM : ").append(vm.getName()).append(" - ").append(vm.getVcpu()).append(" / ").append(vm.getVram());
				builder.append("\n");
			}
			
			builder.append("Total CPU usage : " ).append(Utils.sumVirtualMachinesCpu(vmsOnServer)).append("/").append(server.getCpu()).append("\n");
			builder.append("Total RAM usage : " ).append(Utils.sumVirtualMachinesRam(vmsOnServer)).append("/").append(server.getRam()).append("\n");
			builder.append("Server cost : ").append(server.getCost()).append("\n");
			builder.append("Server reliability : ").append(server.getReliability()).append("\n");
			builder.append("================================================================================ \n");
		}
		
		return builder.toString();
	}
}
