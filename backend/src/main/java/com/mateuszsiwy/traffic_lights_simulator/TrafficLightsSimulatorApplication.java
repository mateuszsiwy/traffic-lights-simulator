package com.mateuszsiwy.traffic_lights_simulator;

import com.mateuszsiwy.traffic_lights_simulator.command.Command;
import com.mateuszsiwy.traffic_lights_simulator.io.JsonReader;
import com.mateuszsiwy.traffic_lights_simulator.io.JsonWriter;
import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class TrafficLightsSimulatorApplication {

	public static void main(String[] args) {
		if (args.length < 2){
			System.out.println("Usage: java -jar traffic-lights-simulator-0.0.1-SNAPSHOT.jar <input_file> <output_file>");
			return;
		}
		String inputFile = args[0];
		String outputFile = args[1];
		try{
			JsonReader reader = new JsonReader();
			List<Command> commands = reader.readCommands(inputFile);

			Simulation simulation = new Simulation();
			for (Command command : commands) {
				command.execute(simulation);
			}

			JsonWriter writer = new JsonWriter();
			writer.writeResults(simulation.getStepStatuses(), outputFile);

			System.out.println("Simulation finished. Results saved to " + outputFile);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
        }
//		SpringApplication.run(TrafficLigthsSimulatorApplication.class, args);
	}

}
