package za.ac.cput.controller.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.parent.Child;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.service.parent.IChildService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/child")
@CrossOrigin(originPatterns = "*")
public class ChildController {

    private final IChildService childService;

    @Autowired
    public ChildController(IChildService childService) {
        this.childService = childService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Child child, @RequestParam(required = false) Integer parentId) {
        try {
            // If parentId is provided as a parameter, use it
            if (parentId != null && child.getParent() == null) {
                Parent parent = new Parent.Builder().setParentId(parentId).build();
                child = new Child.Builder()
                        .copy(child)
                        .setParent(parent)
                        .build();
            }
            
            Child created = childService.create(child);
            Map<String, Object> data = new HashMap<>();
            data.put("childId", created.getChildId());
            data.put("childName", created.getChildName());
            data.put("childSurname", created.getChildSurname());
            data.put("childAge", created.getChildAge());
            data.put("photoUrl", created.getPhotoUrl());
            data.put("parentId", created.getParent() != null ? created.getParent().getParentId() : null);
            return new ResponseEntity<>(data, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Child> read(@PathVariable Integer id) {
        Child child = childService.read(id);
        if (child != null) {
            return new ResponseEntity<>(child, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Child> update(@RequestBody Child child) {
        try {
            Child updated = childService.update(child);
            if (updated != null) {
                return new ResponseEntity<>(updated, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            childService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        List<Child> children = childService.getAll();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Child c : children) {
            Map<String, Object> data = new HashMap<>();
            data.put("childId", c.getChildId());
            data.put("childName", c.getChildName());
            data.put("childSurname", c.getChildSurname());
            data.put("childAge", c.getChildAge());
            data.put("photoUrl", c.getPhotoUrl());
            data.put("parentId", c.getParent() != null ? c.getParent().getParentId() : null);
            list.add(data);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<Map<String, Object>>> getByParentId(@PathVariable Integer parentId) {
        List<Child> children = childService.getChildrenByParent(parentId);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Child c : children) {
            Map<String, Object> data = new HashMap<>();
            data.put("childId", c.getChildId());
            data.put("childName", c.getChildName());
            data.put("childSurname", c.getChildSurname());
            data.put("childAge", c.getChildAge());
            list.add(data);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
