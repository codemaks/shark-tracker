package sharkitter.model;

import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.general.PieDataset;

import java.util.List;

public class SharkDataSet implements PieDataset {
    @Override
    public void addChangeListener(DatasetChangeListener datasetChangeListener) {

    }

    @Override
    public void removeChangeListener(DatasetChangeListener datasetChangeListener) {

    }

    @Override
    public DatasetGroup getGroup() {
        return null;
    }

    @Override
    public void setGroup(DatasetGroup datasetGroup) {

    }

    @Override
    public Comparable getKey(int i) {
        return null;
    }

    @Override
    public int getIndex(Comparable comparable) {
        return 0;
    }

    @Override
    public List getKeys() {
        return null;
    }

    @Override
    public Number getValue(Comparable comparable) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public Number getValue(int i) {
        return null;
    }
}
