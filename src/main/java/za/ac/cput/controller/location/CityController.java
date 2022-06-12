package za.ac.cput.controller.location;
/*
 This is the city controller
 Name & Surname: Charles Lemmert
 Student No: 220498385
*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import za.ac.cput.domain.location.City;
import za.ac.cput.factory.location.CityFactory;
import za.ac.cput.service.location.impl.CityServiceImpl;

import java.util.List;


@RestController
@RequestMapping("api/v1/school-management/city/")
public class CityController {
    private final CityServiceImpl cityService;

    @Autowired
    public CityController(CityServiceImpl cityService) {
        this.cityService = cityService;
    }

    @PostMapping("save")
    public ResponseEntity<City> save(@RequestBody City city) {
        City newCity = CityFactory.buildCity(city.getId(),city.getCityName(),city.getCountry());
        City citySaved = this.cityService.save(newCity);
        return ResponseEntity.ok(citySaved);
    }

    @GetMapping("read/{id}")
    public ResponseEntity<City> read(@PathVariable String id) {
        City readCity = this.cityService.read(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        return ResponseEntity.ok(readCity);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> delete(City city) {
        this.cityService.delete(city);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        this.cityService.deleteCityByID(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("all")
    public ResponseEntity<List<City>> findAllCities() {
        List<City> findAllCitiesList = this.cityService.findAllCities();
        return ResponseEntity.ok(findAllCitiesList);
    }

    @GetMapping("read")
    public ResponseEntity<City> findCityByCountryID(@RequestParam("countryID") String countryID) {
        City readCityByCountry = this.cityService.findCityByCountryID(countryID).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No Cities present/found"));
        return ResponseEntity.ok(readCityByCountry);
    }

}
