package com.dmitrysimakov.kilogram.util

import com.dmitrysimakov.kilogram.data.local.KilogramDb
import com.dmitrysimakov.kilogram.data.local.entity.*

val testMechanicsTypes = arrayListOf(
        MechanicsType("Базовое"),
        MechanicsType("Изолирующее"),
        MechanicsType("Другое")
)

val testExerciseTypes = arrayListOf(
        ExerciseType("Силовое"),
        ExerciseType("Кардио"),
        ExerciseType("Растяжка")
)

val testEquipment = arrayListOf(
        Equipment("Штанга"),
        Equipment("Гантели"),
        Equipment("Гири")
)

val testMuscles = arrayListOf(
        Muscle("Шея"),
        Muscle("Трапеция"),
        Muscle("Плечи"),
        Muscle("Грудь"),
        Muscle("Пресс"),
        Muscle("Бицепсы"),
        Muscle("Трицепсы"),
        Muscle("Предплечья"),
        Muscle("Широчайшие"),
        Muscle("Ромбовидные"),
        Muscle("Поясница"),
        Muscle("Абдукторы"),
        Muscle("Аддукторы"),
        Muscle("Ягодицы"),
        Muscle("Квадрицепсы"),
        Muscle("Бицепсы бедра"),
        Muscle("Икры"),
        Muscle("Комплексные упражнения")
)

val testTargetedMuscles = arrayListOf(
        TargetedMuscle("Жим штанги лёжа", "Трицепсы"),
        TargetedMuscle("Жим штанги лёжа", "Плечи"),
        TargetedMuscle("Становая тяга", "Икры"),
        TargetedMuscle("Становая тяга", "Предплечья"),
        TargetedMuscle("Становая тяга", "Ягодицы"),
        TargetedMuscle("Становая тяга", "Бицепсы бедра"),
        TargetedMuscle("Становая тяга", "Широчайшие"),
        TargetedMuscle("Становая тяга", "Поясница"),
        TargetedMuscle("Становая тяга", "Ромбовидные"),
        TargetedMuscle("Становая тяга", "Квадрицепсы"),
        TargetedMuscle("Становая тяга", "Трапеция"),
        TargetedMuscle("Приседания со штангой", "Икры"),
        TargetedMuscle("Приседания со штангой", "Ягодицы"),
        TargetedMuscle("Приседания со штангой", "Бицепсы бедра"),
        TargetedMuscle("Приседания со штангой", "Поясница")
)

val testExercises = arrayListOf(
        Exercise("Жим штанги лёжа",
                "Грудь",
                "Базовое",
                "Силовое",
                "Штанга",
                "",
                3,
                true
        ),
        Exercise("Становая тяга",
                "Комплексные упражнения",
                "Базовое",
                "Силовое",
                "Штанга",
                "",
                5,
                false
        ),
        Exercise("Приседания со штангой",
                "Квадрицепсы",
                "Базовое",
                "Силовое",
                "Штанга",
                "",
                0,
                false
        )
)

val testPrograms = arrayListOf(
        Program(1, "Push Pull Legs"),
        Program(2, "Full body"),
        Program(3, "Bro split", "Грудь, Спина, Плечи, Руки, Ноги.")
)

val testProgramDays = arrayListOf(
        ProgramDay(1, 2, 1, "Name"),
        ProgramDay(2, 1, 1, "Push"),
        ProgramDay(3, 1, 3, "Pull"),
        ProgramDay(4, 1, 2, "Legs"),
        ProgramDay(5, 3, 1, "Грудь"),
        ProgramDay(6, 3, 2, "Спина")
)

val testProgramDayExercises = arrayListOf(
        ProgramDayExercise(1, 1, "Жим штанги лёжа", 1, 120),
        ProgramDayExercise(2, 1, "Становая тяга", 2, 180)
)

val testProgramDayMuscles = arrayListOf(
        ProgramDayMuscle(5, "Грудь"),
        ProgramDayMuscle(6, "Ромбовидные"),
        ProgramDayMuscle(6, "Широчайшие")
)

val testTrainings = arrayListOf(
        Training(1,  0, 3600, null),
        Training(2,  10000, 3600, 1),
        Training(3,  20000, 3600, 2)
)

val testTrainingMuscles = arrayListOf(
        TrainingMuscle(2, "Грудь"),
        TrainingMuscle(2, "Трицепсы"),
        TrainingMuscle(3, "Квадрицепсы")
)

val testTrainingExercises = arrayListOf(
        TrainingExercise(1, 1, "Жим штанги лёжа", 1, 120),
        TrainingExercise(2, 1, "Становая тяга", 2, 240),
        TrainingExercise(3, 2, "Жим штанги лёжа", 3, 120)
)