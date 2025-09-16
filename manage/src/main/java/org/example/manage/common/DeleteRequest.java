package org.example.manage.common;

import java.util.List;

public class DeleteRequest {
    public DeleteRequest(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    private List<Integer> ids;

}
