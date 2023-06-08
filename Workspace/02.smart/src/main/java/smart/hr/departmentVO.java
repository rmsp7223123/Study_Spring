package smart.hr;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class departmentVO {
	private int department_id, manager_id, location_id;
	private String department_name;
}
