/*******************************************************************************
 * Class        ：CourseSection
 * Created date ：2024/10/28
 * Lasted date  ：2024/10/28
 * Author       ：dungnt3
 * Change log   ：2024/10/28：01-00 dungnt3 create a new
 ******************************************************************************/
package com.unit.studentmgmt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CourseSection
 *
 * @version 01-00
 * @since 01-00
 * @author dungnt3
 */
@Entity
@Table(name = "course_section")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionId;

    @Column(name = "course_id", nullable = false)
    private String courseId;

    @Column(name = "section_name", nullable = false)
    private String sectionName;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;
}
