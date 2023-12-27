package cn.com.goldwind.kis.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_publication_time")
public class HolyPublication implements Serializable {

	private static final long serialVersionUID = -2468009974295166772L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	@Column(name = "volumeName")
	private String volumeName;

	@Column(name = "byliner")
	private String byliner;

	@Column(name = "writingPlace")
	private String writingPlace;

	@Column(name = "finishTime")
	private String finishTime;

	@Column(name = "coverPeriod")
	private String coverPeriod;

}
