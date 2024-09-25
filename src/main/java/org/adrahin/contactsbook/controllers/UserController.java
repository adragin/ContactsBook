package org.adrahin.contactsbook.controllers;

import org.adrahin.contactsbook.model.ErrorResponse;
import org.adrahin.contactsbook.model.ResponseAPI;
import org.adrahin.contactsbook.model.userModels.*;

import org.adrahin.contactsbook.exceptions.EasyUserPasswordException;
import org.adrahin.contactsbook.exceptions.InvalidLoginPasswordException;
import org.adrahin.contactsbook.exceptions.UserAlreadyExistsException;
import org.adrahin.contactsbook.service.InterfaceUserService;
import org.adrahin.contactsbook.utils.UtilsCheckToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "User'ы", description = "Контроллер простой регистрации и авторизации пользователей (таблица users)")
public class UserController {

    @Autowired
    public InterfaceUserService dbUserService;
    @Autowired
    private ResponseAPI responseAPI;
    @Autowired
    private UtilsCheckToken utilsCheckToken;

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @Parameter(name = "password", description = "Для корректности ввода пароль должен содержать:\n" +
            " - только латиницу;\n - минимум 8 символов;\n - минимум 1 большую букву;\n - один из спец.сомволов: !@#$%^&*()-+=<?>")
    @PostMapping(value = "/userRegistration", consumes = "application/json")
    public ResponseEntity<ResponseAPI> userRegistration(@Valid @RequestBody UserDtoRegisterRequest newUser) {
        responseAPI.response = dbUserService.registerUser(newUser);
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @PostMapping("/userLogin")
    public ResponseEntity<ResponseAPI> userAuthorization(@Valid @RequestBody UserDtoLoginRequest userDto) {
        responseAPI.response = dbUserService.loginUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Получить пользователя по id", description = "Работает только если token принадлежит администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseAPI> getUserById(@PathVariable UUID userId,
                                            @RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        if (!dbUserService.isAdmin(token)) {
            responseAPI.response = new ErrorResponse(403, "Access denied.");
            return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
        }

        responseAPI.response = dbUserService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Изменить роль пользователя на ADMIN по id", description = "Работает только если token принадлежит администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<ResponseAPI> setAdminByUserId(@PathVariable UUID userId,
                                                        @RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        if (!dbUserService.isAdmin(token)) {
            return buildErrorResponse(403, "Access denied.");
        }

        User user = dbUserService.getUserById(userId);
        user.setRole(Roles.ADMIN.getRole());
        dbUserService.updateUser(user);
        responseAPI.response = user;

        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Получить список всех пользователей", description = "Работает только если token принадлежит администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @GetMapping()
    public ResponseEntity<ResponseAPI> getAllUsers(@RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        if (!dbUserService.isAdmin(token)) {
            responseAPI.response = new ErrorResponse(403, "Access denied.");
            return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
        }
        List<User> users = dbUserService.getAllUsers();
        responseAPI.response = users;
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    @Operation(summary = "Удалить пользователя по id", description = "Работает только если token принадлежит администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код 200 вернётся в любом случае. Действительный код в поле statusCode ответа.")})
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseAPI> deleteUserById(@PathVariable UUID userId,
                                                      @RequestHeader(value = "token", required = false) String token) {
        ResponseEntity<ResponseAPI> badRequest = utilsCheckToken.isTokenCorrect(token);
        if (badRequest != null) return badRequest;

        if (!dbUserService.isAdmin(token)) {
            return buildErrorResponse(403, "Access denied.");
        }

        boolean isDeleted = dbUserService.deleteUserById(userId);
        if (!isDeleted) {
            return buildErrorResponse(404, "User not found.");
        }
        responseAPI.response = true;

        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    private ResponseEntity<ResponseAPI> buildErrorResponse(int statusCode, String message) {
        responseAPI.response = new ErrorResponse(statusCode, message);
        return ResponseEntity.status(HttpStatus.OK).body(responseAPI);
    }

    //обработка исключений
    @ExceptionHandler({EasyUserPasswordException.class, InvalidLoginPasswordException.class, UserAlreadyExistsException.class})
    public ResponseEntity<UserDtoResponse> handlerCheckDataException(Exception exception) {
        UserDtoResponse response = UserDtoResponse.builder()
                .error(new ErrorResponse(500, exception.getMessage()))
                .build();
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserDtoResponse> handlerCheckLoginException(MethodArgumentNotValidException exception) {
        UserDtoResponse response = UserDtoResponse.builder()
                .error(new ErrorResponse(500, "You login is too easy"))
                .build();
        return ResponseEntity.ok(response);
    }
}
