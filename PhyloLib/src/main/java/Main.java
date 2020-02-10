import cli.Commands;
import data.Context;
import flow.algorithm.Algorithm;
import flow.correction.Correction;
import flow.distance.Distance;
import flow.optimization.Optimization;

public class Main {

    public static void main(String[] args) {
        try {
            Commands commands = new Commands();
            commands.init(args);
            Context context = new Context();
            Distance.run(commands, context);
            Correction.run(commands, context);
            Algorithm.run(commands, context);
            Optimization.run(commands, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
