package com.numergy.blog.csp.problems;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import com.numergy.blog.csp.planning.entity.VirtualMachine;
import com.numergy.blog.csp.planning.fact.ComputeServer;
import com.numergy.blog.csp.planning.solution.VirtualMachinesAssignment;
import com.numergy.blog.csp.util.ComputeFactory;
import com.numergy.blog.csp.util.Utils;

public class VirtualMachinesAssignmentTest {
	private SolverFactory solverFactory = SolverFactory.createFromXmlResource("virtualMachinesAssignmentSolverConfig.xml");
	private Solver solver = solverFactory.buildSolver();

	@Test
	public void nominalTest() {

		// Adapt
		final int[] serversRam = { 32, 32, 32, 32 };
		final int[] serversCpu = { 8, 8, 8, 8 };
		final int[] serversAnnualCosts = { 100, 100, 250, 300 };
		final int[] serversReliability = { 100, 100, 250, 300 };
		
		final int[] vmsRam = { 2, 2, 2, 4, 4, 4, 8, 8, 8, 16, 16 };
		final int[] vmsCpu = { 1, 1, 1, 2, 2, 2, 2, 2, 2, 4, 4 };
		List<VirtualMachine> vms = ComputeFactory.buildVirtualMachines(vmsRam, vmsCpu);
		List<ComputeServer> servers = ComputeFactory.buildComputeServer(serversRam, serversCpu, serversAnnualCosts, serversReliability);

		VirtualMachinesAssignment unsolvedVirtualMachinesAssignment = new VirtualMachinesAssignment();
		unsolvedVirtualMachinesAssignment.setComputeServersList(servers);
		unsolvedVirtualMachinesAssignment.setVirtualMachinesList(vms);

		// Act
		solver.solve(unsolvedVirtualMachinesAssignment);
		VirtualMachinesAssignment solvedVirtualMachinesAssignments = (VirtualMachinesAssignment) solver.getBestSolution();

		// Assert
		assertTrue(solvedVirtualMachinesAssignments.getScore().isFeasible());

		for (VirtualMachine vm : solvedVirtualMachinesAssignments.getVirtualMachinesList()) {
			assertNotNull(vm.getServer());
		}

		for (final ComputeServer server : solvedVirtualMachinesAssignments.getComputeServersList()) {
			Collection<VirtualMachine> vmsOnServer = Utils.findVmsOnServer(server, solvedVirtualMachinesAssignments.getVirtualMachinesList());
			int usedRam = Utils.sumVirtualMachinesRam(vmsOnServer);
			int usedCpu = Utils.sumVirtualMachinesCpu(vmsOnServer);

			assertTrue(usedRam <= server.getRam());
			assertTrue(usedCpu <= server.getCpu());
		}
		
		System.out.println(solvedVirtualMachinesAssignments);
	}

	@Test
	public void testComputePacking_surcontrainte_ram() {
		final int[] serversRam = { 32, 32, 32, 32 };
		final int[] serversCpu = { 8, 8, 8, 8 };
		final int[] serversAnnualCosts = { 100, 100, 250, 300 };
		final int[] serversReliability = { 25, 25, 50, 100 };

		final int[] vmsRam = { 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 8, 8, 8, 16, 16, 32, 32 };
		final int[] vmsCpu = { 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4 };

		List<VirtualMachine> vms = ComputeFactory.buildVirtualMachines(vmsRam, vmsCpu);
		List<ComputeServer> servers = ComputeFactory.buildComputeServer(serversRam, serversCpu, serversAnnualCosts, serversReliability);

		SolverFactory solverFactory = SolverFactory.createFromXmlResource("virtualMachinesAssignmentSolverConfig.xml");
		Solver solver = solverFactory.buildSolver();

		VirtualMachinesAssignment unsolvedVirtualMachinesAssignment = new VirtualMachinesAssignment();
		unsolvedVirtualMachinesAssignment.setComputeServersList(servers);
		unsolvedVirtualMachinesAssignment.setVirtualMachinesList(vms);

		// Act
		solver.solve(unsolvedVirtualMachinesAssignment);
		VirtualMachinesAssignment solvedVirtualMachinesAssignments = (VirtualMachinesAssignment) solver.getBestSolution();

		assertFalse(solvedVirtualMachinesAssignments.getScore().isFeasible());
	}

	@Test
	public void testComputePacking_surcontrainte_cpu() {
		final int[] serversRam = { 32, 32, 32, 32 };
		final int[] serversCpu = { 8, 8, 8, 8 };
		final int[] serversAnnualCosts = { 100, 100, 250, 300 };
		final int[] serversReliability = { 25, 25, 50, 100 };

		final int[] vmsRam = { 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 8, 8, 8, 16, 16 };
		final int[] vmsCpu = { 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 4, 4, 6, 6, 8, 8 };

		List<VirtualMachine> vms = ComputeFactory.buildVirtualMachines(vmsRam, vmsCpu);
		List<ComputeServer> servers = ComputeFactory.buildComputeServer(serversRam, serversCpu, serversAnnualCosts, serversReliability);

		SolverFactory solverFactory = SolverFactory.createFromXmlResource("virtualMachinesAssignmentSolverConfig.xml");
		Solver solver = solverFactory.buildSolver();

		VirtualMachinesAssignment unsolvedVirtualMachinesAssignment = new VirtualMachinesAssignment();
		unsolvedVirtualMachinesAssignment.setComputeServersList(servers);
		unsolvedVirtualMachinesAssignment.setVirtualMachinesList(vms);

		// Act
		solver.solve(unsolvedVirtualMachinesAssignment);
		VirtualMachinesAssignment solvedVirtualMachinesAssignments = (VirtualMachinesAssignment) solver.getBestSolution();

		assertFalse(solvedVirtualMachinesAssignments.getScore().isFeasible());
	}
}
