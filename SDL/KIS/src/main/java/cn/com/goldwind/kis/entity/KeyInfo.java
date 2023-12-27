package cn.com.goldwind.kis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_key_info")
public class KeyInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "key_info_id")
	private String id;

	private String chinese;

	private String english;

	private String form;

	@Column(name = "key_info_image")
	private String image;

	@Column(name = "key_info_remark")
	private String remark;

	@Column(name = "key_info_subject")
	private String subject;

	@Column(name = "key_info_function")
	private String function;

	@Column(name = "key_info_position")
	private String position;

}
