/*******************************************************************************
 * Class        ：StudentDetailDto
 * Created date ：2024/10/28
 * Lasted date  ：2024/10/28
 * Author       ：dungnt3
 * Change log   ：2024/10/28：01-00 dungnt3 create a new
 ******************************************************************************/
package com.unit.studentmgmt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * StudentDetailDto
 *
 * @author dungnt3
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailDto {
    private String fullName;
    private String email;
}
