package com.mysite.samteam.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FirebaseVO {

    @Id
    private String userId;

    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private TblWhgsUserInfo userInfo;

    public FirebaseVO(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
