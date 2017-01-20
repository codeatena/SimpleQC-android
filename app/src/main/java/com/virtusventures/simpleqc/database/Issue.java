package com.virtusventures.simpleqc.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by mac on 11/01/2017.
 */

@Table(database = QCDatabase.class)
public class Issue extends BaseModel implements Serializable {

    @PrimaryKey
    @Column
    String id;

    @Column
    String questionid;

    @Column
    String pid;

    @Column
    Integer issueCount;

    @Column
    String questionEn;

    @Column
    String questionCn;

    public String  getId()
    {
        return id;
    }

    public String getQid()
    {
        return questionid;
    }

    public Integer getIssueCount()
    {
        return issueCount;
    }

    public void setIssueCount(Integer count)
    {
        issueCount = count;
    }

    public String getQuestionEn()
    {
        return questionEn;
    }

    public String getQuestionCn()
    {
        return questionCn;
    }
}
