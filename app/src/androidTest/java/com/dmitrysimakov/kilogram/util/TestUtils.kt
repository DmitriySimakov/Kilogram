package com.dmitrysimakov.kilogram.util

import com.dmitrysimakov.kilogram.data.local.KilogramDb
import com.dmitrysimakov.kilogram.data.local.entity.*

val testMechanicsTypes = arrayListOf(
        MechanicsType(1, "Базовое"),
        MechanicsType(2, "Изолирующее"),
        MechanicsType(3, "Другое")
)

val testExerciseTypes = arrayListOf(
        ExerciseType(1, "Силовое"),
        ExerciseType(2, "Кардио"),
        ExerciseType(3, "Растяжка")
)

val testEquipment = arrayListOf(
        Equipment(1, "Штанга"),
        Equipment(2, "Гантели"),
        Equipment(3, "Гири")
)

val testMuscles = arrayListOf(
        Muscle(1, "Шея"),
        Muscle(2, "Трапеция"),
        Muscle(3, "Плечи"),
        Muscle(4, "Грудь"),
        Muscle(5, "Пресс"),
        Muscle(6, "Косые"),
        Muscle(7, "Бицепсы"),
        Muscle(8, "Трицепсы"),
        Muscle(9, "Предплечья"),
        Muscle(10, "Широчайшие"),
        Muscle(11, "Ромбовидные"),
        Muscle(12, "Поясница"),
        Muscle(13, "Абдукторы"),
        Muscle(14, "Аддукторы"),
        Muscle(15, "Ягодицы"),
        Muscle(16, "Квадрицепсы"),
        Muscle(17, "Бицепсы бедра"),
        Muscle(18, "Икры"),
        Muscle(19, "Комплексные упражнения")
)

val testTargetedMuscles = arrayListOf(
        TargetedMuscle(1, 3),
        TargetedMuscle(1, 8),
        TargetedMuscle(2, 2),
        TargetedMuscle(2, 9),
        TargetedMuscle(2, 10),
        TargetedMuscle(2, 11),
        TargetedMuscle(2, 12),
        TargetedMuscle(2, 15),
        TargetedMuscle(2, 16),
        TargetedMuscle(2, 18),
        TargetedMuscle(3, 12),
        TargetedMuscle(3, 15),
        TargetedMuscle(3, 18)
)

val testExercises = arrayListOf(
        Exercise(1, "Жим штанги лёжа", 4, 1, 1, 1, "", 3, true),
        Exercise(2, "Становая тяга", 19, 1, 1, 1, "", 5, false),
        Exercise(3, "Приседания со штангой", 16, 1, 1, 1, "", 0, false)
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
        ProgramDayExercise(1, 1, 1, 1, 120),
        ProgramDayExercise(2, 1, 2, 2, 120)
)

val testProgramDayMuscles = arrayListOf(
        ProgramDayMuscle(5, 4),
        ProgramDayMuscle(6, 10),
        ProgramDayMuscle(6, 11)
)

val testTrainings = arrayListOf(
        Training(1, null, 0, 3600),
        Training(2, 2, 10000, 3600),
        Training(3, 1, 20000, 3600)
)

val testTrainingMuscles = arrayListOf(
        TrainingMuscle(2, 4),
        TrainingMuscle(2, 8),
        TrainingMuscle(3, 1)
)

val testTrainingExercises = arrayListOf(
        TrainingExercise(1, 1, 1, 1, 120),
        TrainingExercise(2, 1, 2, 2, 120),
        TrainingExercise(3, 2, 1, 3, 120)
)