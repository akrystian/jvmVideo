package pro.adamski.jvmvideo.entity;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * @author akrystian.
 */
@Entity
public abstract class Source {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    @NotNull
    private Timestamp lastHarvested;


    public Source() {
        //hibernate entity
        lastHarvested = new Timestamp(0L);
    }

    @SuppressWarnings("squid:S2637")
    public Source(String name, DateTime lastHarvested) {
        this.name = name;
        this.lastHarvested = new Timestamp(notNull(lastHarvested).getMillis());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DateTime getLastHarvested() {
        return new DateTime(lastHarvested);
    }

    public void setLastHarvested(DateTime lastHarvested) {
        this.lastHarvested = new Timestamp(lastHarvested.getMillis());
    }

    public abstract String getSourceLink();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Source)) {
            return false;
        }
        Source source = (Source) o;
        return id == source.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
