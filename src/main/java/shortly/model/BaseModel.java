package shortly.model;

import org.springframework.beans.factory.annotation.*;
import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;

@MappedSuperclass
public class BaseModel {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
}
