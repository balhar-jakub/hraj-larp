package cz.hrajlarp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 28.4.13
 * Time: 10:58
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "authorized_editor", schema = "public", catalog = "")
@Entity
public class AuthorizedEditorEntity {
    private Integer id;

    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorizedEditorEntity that = (AuthorizedEditorEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
