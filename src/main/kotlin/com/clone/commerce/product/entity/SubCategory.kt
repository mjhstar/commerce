package com.clone.commerce.product.entity

import com.clone.commerce.common.extension.TimeUtils
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity(name = "sub_category")
class SubCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idx: Long = 0,
    @Column(unique = true)
    val name: String,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainCategory_idx")
    val mainCategory: MainCategory,
    @JsonIgnore
    @OneToMany(mappedBy = "subCategory", fetch = FetchType.EAGER)
    val detailCategory: List<DetailCategory> = emptyList(),
    val createdAt: Long = TimeUtils.currentTimeMillis(),
    val updatedAt: Long? = null,
    val createdBy: String,
    val updatedBy: String? = null
) {
}
