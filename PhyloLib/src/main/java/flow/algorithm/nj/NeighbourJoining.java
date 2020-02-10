package flow.algorithm.nj;

import cli.Options;
import data.Context;
import data.tree.Cluster;
import data.tree.Pair;
import flow.algorithm.Algorithm;

public abstract class NeighbourJoining extends Algorithm {

    NeighbourJoining(Context context, Options options) throws Exception {
        super(context, options);
    }


    @Override
    protected Pair<Cluster, Cluster> select() {
        return null;
    }

    @Override
    protected Pair<Double, Double> join(Pair<Cluster, Cluster> clusters) {
        return null;
    }

    @Override
    protected void reduce(Pair<Double, Double> distances) {

    }

}
