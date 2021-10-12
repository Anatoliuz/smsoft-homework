package etp.mos.ru.homework1.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("props")
public class Props {

    private String filePath;
    private Integer threshold;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

}
