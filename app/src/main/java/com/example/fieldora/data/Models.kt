package com.example.fieldora.data

data class UserProfile(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val role: UserRole = UserRole.FIELD_EXECUTIVE,
    val branchId: String = "branch_hq",
    val territoryId: String = "territory_north",
    val department: String = "Sales",
    val profilePhoto: String = "",
    val phone: String = "+91 98765 43210",
    val designation: String = "Field Executive"
)

enum class UserRole {
    SUPER_ADMIN,
    ADMIN,
    MANAGER,
    FIELD_EXECUTIVE,
    VIEWER
}

data class AttendanceRecord(
    val attendanceId: String = "",
    val employeeId: String = "",
    val employeeName: String = "",
    val date: String = "",
    val checkInTime: String = "",
    val checkOutTime: String = "",
    val checkInLat: Double = 0.0,
    val checkInLng: Double = 0.0,
    val checkOutLat: Double = 0.0,
    val checkOutLng: Double = 0.0,
    val distanceTravelledKm: Double = 0.0,
    val status: AttendanceStatus = AttendanceStatus.PRESENT,
    val selfieUrl: String = ""
)

enum class AttendanceStatus {
    PRESENT, LATE, HALF_DAY, ABSENT, FIELD_DUTY
}

data class Customer(
    val customerId: String = "",
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val customerType: String = "Retailer",
    val territory: String = "North Zone",
    val status: CustomerStatus = CustomerStatus.ACTIVE,
    val lastVisit: String = "2026-09-10",
    val nextFollowUp: String = "2026-09-15",
    val notes: String = ""
)

enum class CustomerStatus {
    PROSPECT, ACTIVE, INACTIVE, CONVERTED, LOST
}

data class Lead(
    val leadId: String = "",
    val customerName: String = "",
    val product: String = "Enterprise POS",
    val expectedValue: Double = 50000.0,
    val stage: LeadStage = LeadStage.NEW,
    val owner: String = "Rahul Sharma",
    val probability: Int = 50,
    val nextFollowUp: String = "2026-09-16"
)

enum class LeadStage {
    NEW, CONTACTED, INTERESTED, FOLLOW_UP, PROPOSAL, NEGOTIATION, WON, LOST
}

data class Visit(
    val visitId: String = "",
    val customerName: String = "",
    val employeeName: String = "",
    val purpose: String = "Product Demo",
    val timestamp: String = "",
    val outcome: VisitOutcome = VisitOutcome.SUCCESSFUL,
    val notes: String = "",
    val gpsLat: Double = 28.6139,
    val gpsLng: Double = 77.2090
)

enum class VisitOutcome {
    SUCCESSFUL, INTERESTED, FOLLOW_UP_REQUIRED, NOT_INTERESTED, CONVERTED
}

data class TaskItem(
    val taskId: String = "",
    val title: String = "",
    val description: String = "",
    val assignedTo: String = "",
    val dueDate: String = "",
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val status: TaskStatus = TaskStatus.PENDING
)

enum class TaskPriority { LOW, MEDIUM, HIGH, URGENT }
enum class TaskStatus { PENDING, IN_PROGRESS, COMPLETED, OVERDUE }

data class Campaign(
    val campaignId: String = "",
    val name: String = "",
    val campaignType: String = "Retail Promotion",
    val startDate: String = "",
    val endDate: String = "",
    val targetVisits: Int = 100,
    val completedVisits: Int = 45,
    val budget: Double = 25000.0,
    val status: String = "Active"
)

data class Expense(
    val expenseId: String = "",
    val employeeName: String = "",
    val category: String = "Fuel",
    val amount: Double = 450.0,
    val date: String = "",
    val description: String = "Client visit fuel expense",
    val status: ExpenseStatus = ExpenseStatus.SUBMITTED
)

enum class ExpenseStatus { DRAFT, SUBMITTED, APPROVED, REJECTED, PAID }

data class SurveyForm(
    val formId: String = "",
    val title: String = "",
    val description: String = "",
    val questionsCount: Int = 5,
    val isPublished: Boolean = true
)

data class AuditLogEntry(
    val auditId: String = "",
    val actor: String = "",
    val action: String = "",
    val module: String = "",
    val timestamp: String = ""
)

data class NotificationItem(
    val notificationId: String = "",
    val title: String = "",
    val message: String = "",
    val timestamp: String = "",
    val read: Boolean = false
)

data class EmployeeLocationState(
    val employeeId: String = "",
    val employeeName: String = "",
    val latitude: Double = 28.6139,
    val longitude: Double = 77.2090,
    val lastUpdated: String = "10:45 AM",
    val status: LocationStateStatus = LocationStateStatus.LIVE,
    val territory: String = "North Zone",
    val currentTask: String = "Doctor Clinic Visit"
)

enum class LocationStateStatus {
    LIVE, RECENT, OFFLINE, GPS_UNAVAILABLE, PERMISSION_DENIED
}

data class LocationRoutePoint(
    val time: String = "",
    val locationName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val activityType: String = "Visit"
)

data class GeofenceZone(
    val geofenceId: String = "",
    val name: String = "",
    val type: String = "Clinic",
    val latitude: Double = 28.6139,
    val longitude: Double = 77.2090,
    val radiusMeters: Int = 100,
    val status: String = "Active"
)

