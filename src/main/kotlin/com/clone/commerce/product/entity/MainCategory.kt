package com.clone.commerce.product.entity

import com.clone.commerce.common.extension.TimeUtils
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity(name = "main_category")
class MainCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long = 0,
    @Column(unique = true)
    val name: String,
    @JsonIgnore
    @OneToMany(mappedBy = "mainCategory", fetch = FetchType.EAGER)
    val subCategory: List<SubCategory> = emptyList(),
    val createdAt: Long = TimeUtils.currentTimeMillis(),
    val updatedAt: Long? = null,
    val createdBy: String,
    val updatedBy: String? = null
) {
}
