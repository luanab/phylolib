package flow.optimization;

import cli.Options;
import data.Context;
import data.tree.Edge;

public final class LBR extends Optimization {

    LBR(Context context, Options options) throws Exception {
        super(context, options);
    }

    @Override
    protected Edge select() {
        return null;
    }

    @Override
    protected Edge join() {
        return null;
    }

    @Override
    protected void reduce(Edge edge) {

    }

}
