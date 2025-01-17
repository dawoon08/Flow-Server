package com.flow.category;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flow.model.GetCategoryRes;
import com.flow.model.PatchCategoryReq;
import com.flow.model.PostCategoryReq;

@Repository
public class CategoryDao {
    
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //카테고리 추가
    public int createCategory(int userId, PostCategoryReq postCategoryReq) {
        String postCategoryQuery = "insert into category (categoryId, userId, name, typeId, isUserCreated) values ((select ifnull(max(categoryId)+1,1) from category c),?,?,?,1)";
        Object[] postCategoryParams = new Object[]{userId, postCategoryReq.getName(), postCategoryReq.getTypeId()};

        return this.jdbcTemplate.update(postCategoryQuery, postCategoryParams);
    }

    //카테고리 목록 조회
    public List<GetCategoryRes> getCategorys(int userId) {
        String getCategoryQuery = "select * from category where userId = ? and isUserCreated = true";
        Object[] getCategoryParams = new Object[]{userId};

        return this.jdbcTemplate.query(getCategoryQuery, 
            (rs, rowNum) -> new GetCategoryRes(
                rs.getInt("categoryId"), 
                rs.getString("name"), 
                rs.getInt("typeId")),
                getCategoryParams
        );
    }

    //카테고리 수정
    public int modifyCategory(int userId, int categoryId, PatchCategoryReq patchCategoryReq) {
        String modifyCategoryQuery = "update category set name = ?, typeId = ? where userId = ? and categoryId = ?";
        Object[] modifyCategoryParams = new Object[]{patchCategoryReq.getName(), patchCategoryReq.getTypeId(), userId, categoryId};

        return this.jdbcTemplate.update(modifyCategoryQuery, modifyCategoryParams);
    }

    //카테고리 삭제
    public int deleteCategory(int userId, int categoryId) {
        String deleteCategoryQuery = "delete from category where userId = ? and categoryId = ?";
        Object[] deleteCategoryParams = new Object[]{userId, categoryId};

        return this.jdbcTemplate.update(deleteCategoryQuery, deleteCategoryParams);
    }

    //카테고리 삭제 - 해당 카테고리 내역 이동
    //기타지출로 이동
    public int modifyCategoryToEtc(int userId, int categoryId) {
        String modifyCategoryToEtcQuery = "update detail set categoryId = 15 where userId = ? and categoryId = ? and typeId = 1";
        Object[] modifyCategoryToEtcParams = new Object[]{userId, categoryId};

        return this.jdbcTemplate.update(modifyCategoryToEtcQuery, modifyCategoryToEtcParams);
    }

    //수입으로 이동
    public int modifyCategoryToIncome(int userId, int categoryId) {
        String modifyCategoryToIncomeQuery = "update detail set categoryId = 16 where userId = ? and categoryId = ? and typeId = 2";
        Object[] modifyCategoryToIncomeParams = new Object[]{userId, categoryId};

        return this.jdbcTemplate.update(modifyCategoryToIncomeQuery, modifyCategoryToIncomeParams);
    }

    //카테고리 연관 키워드 삭제
    public int deleteCategoryKeyword(int userId, int categoryId) {
        String deleteCategoryQuery = "delete from keyword where userId = ? and categoryId = ?";
        Object[] deleteCategoryParams = new Object[]{userId, categoryId};

        return this.jdbcTemplate.update(deleteCategoryQuery, deleteCategoryParams);
    }

}
