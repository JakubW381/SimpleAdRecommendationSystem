package dev.jakubw.domain.port.in.impression;

public interface RecordImpressionCmd {
    void execute(String adId);
}