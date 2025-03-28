package com.mateuszsiwy.traffic_lights_simulator;

import com.mateuszsiwy.traffic_lights_simulator.command.Command;
import com.mateuszsiwy.traffic_lights_simulator.io.JsonReader;
import com.mateuszsiwy.traffic_lights_simulator.io.JsonWriter;
import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TrafficLightsSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrafficLightsSimulatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(JsonReader reader, JsonWriter writer) {
        return args -> {
            if (args.length >= 2) {
                String inputFile = args[0];
                String outputFile = args[1];

                try {
                    List<Command> commands = reader.readCommands(inputFile);

                    Simulation simulation = new Simulation();
                    for (Command command : commands) {
                        command.execute(simulation);
                    }

                    writer.writeResults(simulation.getStepStatuses(), outputFile);

                    System.out.println("Simulation finished. Results saved to " + outputFile);

                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
    }

    @Bean
    public JsonReader jsonReader() {
        return new JsonReader();
    }

    @Bean
    public JsonWriter jsonWriter() {
        return new JsonWriter();
    }
}