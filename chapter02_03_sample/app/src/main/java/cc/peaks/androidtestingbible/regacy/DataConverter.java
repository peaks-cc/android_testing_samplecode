package cc.peaks.androidtestingbible.regacy;

public class DataConverter {
    NewData convert(OldData oldData) {
        return new NewData("converted:" + oldData.data);
    }
}
